package com.acgnu.origin.mapper;

import com.acgnu.origin.model.SimpleModel;
import com.acgnu.origin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SimpleMapper {
    @Select("select * from card_msg limit 2")
    List<SimpleModel> findAll();

    @Select("select * from card_msg where id = #{id}")
    SimpleModel findOne(@Param("id") Integer id);

    List<SimpleModel> queryOne(@Param("id") Integer id);

    @Select("select * from t_users where id = #{id}")
    User findOneUser(@Param("id") Integer id);
}
