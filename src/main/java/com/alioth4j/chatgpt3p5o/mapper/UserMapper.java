package com.alioth4j.chatgpt3p5o.mapper;

import com.alioth4j.chatgpt3p5o.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);

    @Insert("insert into `User` (id, username, password, phone, icon, create_time, point) " +
            "values (#{id}, #{username}, #{password}, #{phone}, #{icon}, #{createTime}, #{point})")
    void insert(User user);
}
