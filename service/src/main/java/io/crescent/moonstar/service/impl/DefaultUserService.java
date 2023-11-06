package io.crescent.moonstar.service.impl;

import io.crescent.moonstar.entity.UserEntity;
import io.crescent.moonstar.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

  @Override
  public UserEntity addUser(String userName, String password) {
    return null;
  }

  @Override
  public boolean updateUser(UserEntity user) {
    return false;
  }

  @Override
  public int testTransaction(UserEntity user) {
    return 0;
  }
}
