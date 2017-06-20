package com.github.springboot.mapper;

import com.github.springboot.domain.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 */
public interface UserMapper {

  //    @Insert("insert into user (username,password) values (#{username},#{password})")
  public int addUser(User user);

  @Update("update user set username = #{userName}, password = #{password} where userid = #{userId}")
  public int updateUser(User user);

  @Select("select * from user where userid = #{userId}")
  public User query(int userId);

  public int count();

  public List<User> getUsersByPage(User user);
}
