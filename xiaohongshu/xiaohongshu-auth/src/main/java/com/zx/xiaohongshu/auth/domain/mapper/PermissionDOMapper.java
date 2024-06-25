package com.zx.xiaohongshu.auth.domain.mapper;

import com.zx.xiaohongshu.auth.domain.dataobject.PermissionDO;

import java.util.List;

public interface PermissionDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PermissionDO record);

    int insertSelective(PermissionDO record);

    PermissionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionDO record);

    int updateByPrimaryKey(PermissionDO record);

    /**
     * 查询APP端所有被启用的权限
     *
     * @return
     */
    List<PermissionDO> selectAppEnabledList();

}









