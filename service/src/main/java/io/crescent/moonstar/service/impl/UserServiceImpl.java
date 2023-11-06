package io.crescent.moonstar.service.impl;

import io.crescent.moonstar.entity.UserEntity;
import io.crescent.moonstar.entity.UserLogEntity;
import io.crescent.moonstar.mapper.UserLogMapper;
import io.crescent.moonstar.mapper.UserMapper;
import io.crescent.moonstar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
//@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLogMapper userLogMapper;

    // TODO 密码需要加密
    @Override
    public UserEntity addUser(String userName, String password) {
        UserEntity user = new UserEntity().setPassword(password).setUserName(userName);
        userMapper.addUser(user);
        return user;
    }

    @Override
    @Transactional
    public boolean updateUser(UserEntity user) {
        boolean res = userMapper.updateUser(user) > 0;
        if ("lhj02".equalsIgnoreCase(user.getUserName())) {
            throw new IllegalArgumentException("it's a test");
        }
        userLogMapper.insert(new UserLogEntity().setUserId(user.getUserId()).setOperatorType("update"));
        return res;
    }

    /**
     * 当testTransaction失败产生异常时，虽然数据没有被写入到数据库，但是数据库user表的自增id增加了， 说明插入动作引起了数据库唯一id的改变，事务一场被还原后id没有还原
     */
    @Transactional
    @Override
    public int testTransaction(UserEntity user) {
        userMapper.addUser(user);
        logger.info("test transaction. continue...");
        throw new IllegalArgumentException("it's a test");
    }
}
