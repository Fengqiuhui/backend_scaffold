package com.maoniunet.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.maoniunet.common.SuperMapper;
import com.maoniunet.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 数据库接口 MyBatis + MyBatis Plus
 * 强制: 接口命名为
 * selectOneByxxx (一条数据)
 * selectListByxxx (多条数据)
 * selectPageByxxx (分页数据)
 *
 * update delete 同上
 *
 * 如果使用 JPA 则命名规则按 JPA 规范
 * findOneByxxx
 * findAllByxxx
 *
 * 其余同上
 */
@Mapper
public interface UserMapper extends SuperMapper<UserEntity> {

    /**
     * 只取一条数据的 sql 最后要加上 limit 1
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username} limit 1")
    UserEntity selectOneByUsername(String username);

    /**
     * 多个检索条件的情况
     * 方法字段顺序必须与sql字段顺序一致
     * 字段排序按照 关联表id, 本表id, 本表索引字段, 本表无索引字段
     * @param id
     * @param username
     * @return
     */
    @Select("select * from user where id = #{id} and #{username}")
    List<UserEntity> selectListByUsername(Long id, String username);

    /**
     * 对于分布查询
     * 分页条件要放第一位, 其余如上
     * @param page
     * @param wrapper
     * @return
     */
    Page<UserEntity> selectPageByQuery(Page page, Wrapper wrapper);
}
