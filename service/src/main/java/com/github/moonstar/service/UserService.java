package com.github.moonstar.service;

import com.github.moonstar.entity.UserEntity;

public interface UserService {


    UserEntity addUser(String userName, String password);

    boolean updateUser(UserEntity user);

    int testTransaction(UserEntity user);
}
