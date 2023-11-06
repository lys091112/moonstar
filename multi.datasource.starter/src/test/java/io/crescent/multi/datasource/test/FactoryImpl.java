package io.crescent.multi.datasource.test;

import io.crescent.multi.datasource.annotation.DS;

@DS("factoryImpl")
public class FactoryImpl implements IFactory<Integer> {

  @Override
  public void create(Integer integer) {
    System.out.println("--------");
  }
}
