package com.github.moonstar.mapper;

import com.github.moonstar.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "userMapper")
public interface UserMapper {

    @Insert("insert into user set (username, password) values (#{userName},#{password})")
    int addUser(UserEntity user);

    @Update("update user set username = #{userName}, password = #{password} where userid = #{userId}")
    int updateUser(UserEntity user);

    @Select("select * from user where userid = #{userId}")
    UserEntity query(int userId);

    @Select("select count(1) from user")
    int count();
}
