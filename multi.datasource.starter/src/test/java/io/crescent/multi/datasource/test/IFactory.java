package io.crescent.multi.datasource.test;

import io.crescent.multi.datasource.annotation.DS;

public interface IFactory<T> {

  @DS("hello")
  void create(T t);

}
