package com.zx.xiaohongshu.auth;

import com.zx.framework.common.util.JsonUtils;
import com.zx.xiaohongshu.auth.domain.dataobject.UserDO;
import com.zx.xiaohongshu.auth.domain.mapper.UserDOMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j //便于调用日志的
class XiaohongshuAuthApplicationTests {

    @Resource
    private UserDOMapper userDOMapper;

    /**
      *  测试插入数据
      */
    @Test
    void TestInsert() {
        UserDO userDO= UserDO.builder()
                .username("wzx")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                       .build();
        userDOMapper.insert(userDO);
    }

    /**
     * 查询数据
     */
    @Test
    void testSelect(){
        //查询主键ID为4的记录
        UserDO userDO=userDOMapper.selectByPrimaryKey(1L);
        log.info("User:{}", JsonUtils.toJsonString(userDO));
    }

    /**
     * 更新数据
     */
    @Test
     void testUpdate(){
         UserDO userDO= UserDO.builder()
                 .id(2L)
                 .username("wzxxx")
                 .updateTime(LocalDateTime.now())
                      .build();

         userDOMapper.updateByPrimaryKey(userDO);
     }

    /**
     * 删除数据
     */
    @Test
    void testDelete(){
        userDOMapper.deleteByPrimaryKey(2L);
    }

}
