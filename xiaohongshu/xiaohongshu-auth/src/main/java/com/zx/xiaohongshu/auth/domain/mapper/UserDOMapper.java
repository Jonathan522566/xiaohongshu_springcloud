package com.zx.xiaohongshu.auth.domain.mapper;
import  com.zx.xiaohongshu.auth.domain.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;


public interface UserDOMapper {
    /**
     * 根据主键id查询
     * @param id
     * @return
     */
    UserDO selectByPrimarykey(Long id);

    /**
     * 根据主键id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     *插入记录
     * @param record
     * @return
     */
    int insert(UserDO record);

    /**
     *更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(UserDO record);
}
