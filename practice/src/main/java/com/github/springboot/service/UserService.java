package com.github.springboot.service;

import com.github.springboot.entity.User;
import com.github.springboot.mapper.UserMapper;
import com.github.springboot.support.springusetest.CommonTestAutoConfig;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@AutoConfigureAfter(CommonTestAutoConfig.class)
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    /**
     * 通过这个实例证明@Value的初始化在PostConstruct之前
     */
    @PostConstruct
    public void init() {
    }

    public User addUser(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        userMapper.addUser(user);
        return user;
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }


    /**
     *  当testTransaction失败产生异常时，虽然数据没有被写入到数据库，但是数据库user表的自增id增加了，
     *  说明插入动作引起了数据库唯一id的改变，事务一场被还原后id没有还原
     */
    @Transactional
    public int testTransaction(User user) {
        userMapper.addUser(user);
        logger.info("test transaction. continue...");
        throw new IllegalArgumentException("it's a test");
    }

    public List<User> getUsersByPage(int pageIndex) {
        User user = new User();
        user.setPageIndex(pageIndex);
        return userMapper.getUsersByPage(user);
    }

    public List<User> cacheTest() {
        User user = new User().setUserId(1).setUserName("hell0").setPassword("xx");
        return Collections.singletonList(user);
    }
}
