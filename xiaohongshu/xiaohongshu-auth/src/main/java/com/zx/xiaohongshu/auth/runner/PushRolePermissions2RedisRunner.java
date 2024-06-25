package com.zx.xiaohongshu.auth.runner;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zx.framework.common.util.JsonUtils;
import com.zx.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zx.xiaohongshu.auth.domain.dataobject.PermissionDO;
import com.zx.xiaohongshu.auth.domain.dataobject.RoleDO;
import com.zx.xiaohongshu.auth.domain.dataobject.RolePermissionDO;
import com.zx.xiaohongshu.auth.domain.mapper.PermissionDOMapper;
import com.zx.xiaohongshu.auth.domain.mapper.RoleDOMapper;
import com.zx.xiaohongshu.auth.domain.mapper.RolePermissionDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PushRolePermissions2RedisRunner implements ApplicationRunner {

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private RoleDOMapper roleDOMapper;
    @Resource
    private PermissionDOMapper permissionDOMapper;
    @Resource
    private RolePermissionDOMapper rolePermissionDOMapper;

    //权限同步标记Key
    private static final String PUSH_PERMISSION_FLAG="push.permission.flag";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("==>服务启动,开始同步角色权限数据到 Redis 中.....");

       try{
           //是否能够同步数据:原子操作,只有在键PUSH_PERMISSION_FLAG不存在时(未枷锁),
           //才会设置该键的值为“1”,并设置过期时间为1天
           boolean canPushed=redisTemplate.opsForValue().setIfAbsent(PUSH_PERMISSION_FLAG,"1",1, TimeUnit.DAYS);

           //如果无法同步权限数据
           if(!canPushed){
               log.warn("==>角色权限数据已经同步至Redis中,不再同步...");
               return;
           }

           //查询中所有角色(里面有多个字段信息)
           List<RoleDO> roleDOS=roleDOMapper.selectEnabledList();

           if(CollUtil.isNotEmpty(roleDOS)){
               //把角色的所有id取出来
               List<Long> roleIds=roleDOS.stream().map(RoleDO::getId).toList();

               //根据角色ID,批量查询出所有角色对应的权限
               List<RolePermissionDO> rolePermissionDOS=rolePermissionDOMapper.selectByRoleIds(roleIds);

               //1.按角色ID分组,每个角色ID对应多个权限ID  Map([角色id],[角色id对应的权限id])
               Map<Long,List<Long>> roleIdPermissionIdsMap=rolePermissionDOS.stream().collect(
                       Collectors.groupingBy(RolePermissionDO::getRoleId,  //id
                               Collectors.mapping(     //根据集合映射成一个集合
                                       RolePermissionDO::getPermissionId,
                                       Collectors.toList())
                       )
               );

               //查询 APP 端所有被启用的权限
               List<PermissionDO> permissionDOS=permissionDOMapper.selectAppEnabledList();

               //2.权限ID - 权限DO  Map([权限id],[权限内容])
               Map<Long,PermissionDO> permissionDOMap=permissionDOS.stream().collect(
                 Collectors.toMap(PermissionDO::getId,permissionDO -> permissionDO)
               );

               //组织 角色ID-权限内容 关系
               Map<Long,List<PermissionDO>> roleIdPermissionDOMap= Maps.newHashMap();

               //循环所有角色 把 角色id-权限内容 搭建起来
               roleDOS.forEach(roleDO -> {
                   //当前角色ID
                   Long roleId=roleDO.getId();
                   //当前角色ID对应的权限ID集合
                   List<Long> permissionIds=roleIdPermissionIdsMap.get(roleId);
                   if(CollUtil.isNotEmpty(permissionIds)){
                      List<PermissionDO> perDOS= Lists.newArrayList();
                      permissionIds.forEach(permissionId->{
                          //根据权限ID获取具体权限DO对象
                          PermissionDO permissionDO=permissionDOMap.get(permissionId);
                          perDOS.add(permissionDO);
                      });
                      roleIdPermissionDOMap.put(roleId,perDOS);
                   }
               });

               //同步到redis中,方便后续网关查询鉴权使用
               roleIdPermissionDOMap.forEach((roleId,permissionDO)->{
                    String key= RedisKeyConstants.buildRolePermissionsKey(roleId);
                    redisTemplate.opsForValue().set(key, JsonUtils.toJsonString(permissionDO));
               });

           }

           log.info("==>服务启动,成功同步角色权限数据到Redis中.....");
       }catch (Exception e){
          log.error("==> 同步角色权限数据到 Redis 中失败:",e);
       }

    }
}
















