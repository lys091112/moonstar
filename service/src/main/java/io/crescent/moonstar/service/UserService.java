package io.crescent.moonstar.service;

import io.crescent.moonstar.entity.UserEntity;

public interface UserService {


    UserEntity addUser(String userName, String password);

    boolean updateUser(UserEntity user);

    int testTransaction(UserEntity user);
}
