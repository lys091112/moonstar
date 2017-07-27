package com.github.springboot.dao;

import com.github.springboot.domain.User;
import com.github.springboot.Application;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 *
 * @Transactional 这个注解表示使用事务
 * @Rollback() 这个表示方法执行完以后回滚事务，如果设置为false，则不回滚
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;


    @Before
    public void setUp() {
    }

    @Test
    @Transactional
    @Rollback()
    public void test_addUser() {
        User user = new User(1, "user1", "111");
        userDao.addUser(user);
        User user1 = userDao.query(user.getUserId());
        System.out.println(user1.getUserName());
        System.out.println(user1.getPassword());
        assertTrue(user.equals(user1));
    }

    @Test
    //这个注解表示使用事务
    @Transactional
    //这个表示方法执行完以后回滚事务，如果设置为false，则不回滚
    @Rollback()
    public void test_updateUser() {
        User user = new User(0, "user1", "111");
        userDao.addUser(user);

        User user1 = userDao.query(user.getUserId());
        assertThat(user1.getUserName(), Matchers.equalTo("user1"));

        user1.setUserName("newUser");
        userDao.updateUser(user1);
        User user2 = userDao.query(user.getUserId());
        System.out.println(user1.getUserName());
        assertThat(user2.getUserName(), Matchers.equalTo("newUser"));
    }

}