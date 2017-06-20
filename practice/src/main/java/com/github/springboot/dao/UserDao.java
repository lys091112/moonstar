package com.github.springboot.dao;

import com.github.springboot.domain.User;
import com.github.springboot.exception.TransException;
import com.github.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xianyue
 */

@Repository
public class UserDao {
    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public User query(int userId) {
       return userMapper.query(userId);
    }

    public int testTransaction(User user) {
        if (user.getUserName().startsWith("trans")) {
            throw new TransException("this is a transaction test");
        }
        return 1;
    }

    public int count() {
        return userMapper.count();
    }

    public List<User> getUsersByPage(User user) {
       return userMapper.getUsersByPage(user) ;
    }
}
