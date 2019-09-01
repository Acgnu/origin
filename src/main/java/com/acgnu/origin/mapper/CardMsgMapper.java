package com.acgnu.origin.mapper;

import com.acgnu.origin.model.CardMsg;
import com.acgnu.origin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CardMsgMapper {
    @Select("select * from card_msg limit 2")
    List<CardMsg> findAll();

    @Select("select * from card_msg where id = #{id}")
    CardMsg findOne(@Param("id") Integer id);

    List<CardMsg> queryOne(@Param("id") Integer id);

    @Select("select * from t_users where id = #{id}")
    User findOneUser(@Param("id") Integer id);
}
