package com.byrsh.mybatisgeneratorcomment.dao;

import com.byrsh.mybatisgeneratorcomment.model.User;
import com.byrsh.mybatisgeneratorcomment.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 计算像example这样的记录数量
     */
    int countByExample(UserExample example);

    /**
     * 根据example删除，注意是物理删除
     */
    int deleteByExample(UserExample example);

    /**
     * 根据数据库ID删除，注意是物理删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条记录
     */
    int insert(User record);

    /**
     * 插入一条记录，和insert的区别是当某个字段的值是null时，便没有该字段，其值将是表设置的默认值
     */
    int insertSelective(User record);

    /**
     * 根据example查询，如果example中含有成员变量limit、offset且不为null，则分页查询
     */
    List<User> selectByExample(UserExample example);

    /**
     * 根据数据库ID查询
     */
    User selectByPrimaryKey(Long id);

    /**
     * 根据example选择性的更新，若字段的值为null，则该字段不更新
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * 根据example更新
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * 根据数据库ID选择性更新
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据数据库ID更新
     */
    int updateByPrimaryKey(User record);

    /**
     */
    int deleteLogicById(@Param("delete") int delete, @Param("id") Long id);
}