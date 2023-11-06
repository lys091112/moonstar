package io.crescent.moonstar.mapper;

import io.crescent.moonstar.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
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
