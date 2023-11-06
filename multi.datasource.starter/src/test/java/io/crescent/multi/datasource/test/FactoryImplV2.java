package io.crescent.multi.datasource.test;

public class FactoryImplV2 extends FactoryImpl {

  @Override
  public void create(Integer integer) {
    System.out.println(this.getClass().getName());
  }
}
