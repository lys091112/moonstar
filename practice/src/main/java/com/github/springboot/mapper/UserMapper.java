package com.github.springboot.mapper;

import com.github.springboot.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

  //    @Insert("insert into user (username,password) values (#{username},#{password})")
  int addUser(User user);

  @Update("update user set username = #{userName}, password = #{password} where userid = #{userId}")
  int updateUser(User user);

  @Select("select * from user where userid = #{userId}")
  User query(int userId);

  int count();

  List<User> getUsersByPage(User user);
}
