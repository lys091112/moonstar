package io.crescent.multi.datasource.test;

import io.crescent.multi.datasource.annotation.DS;
import java.lang.reflect.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class MTest {

  @Test
  public void t1() {
    DS an;
    Method[] methods = IFactory.class.getDeclaredMethods();
    for (Method m : methods) {
      if ((an = m.getAnnotation(DS.class)) != null) {
        System.out.println("an =" + an.value());
      }
      System.out.println(m + ", declareClass=" + m.getDeclaringClass());
    }
    System.out.println("---------");

    methods = FactoryImpl.class.getDeclaredMethods();
    for (Method m : methods) {
      if ((an = m.getAnnotation(DS.class)) != null) {
        System.out.println("an =" + an.value());
      }
      System.out.println(m + ", declareClass=" + m.getDeclaringClass());
    }
    System.out.println("---------");

    // 在类继承关系中：子类会继承获得父类上的那些被@Inherited修饰的注解。
    an = FactoryImplV2.class.getAnnotation(DS.class);
    if (an != null) {
      System.out.println("FactoryImplV2's annotation is " + an.value());
    }
  }

  public void t() {
//    @Bean("myBeanDataSource")
//    public DataSource myBeanDataSourc(@Qualifier("examinDataSource") DataSource examineDataSource,
//        @Qualifier("illegalDetailDataSource") DataSource illegalDetailDataSource) {
//      Map<Object, Object> targetSource = new HashMap<>();
//      targetSource.put(DataSourceType.EXAMINED, examineDataSource);
//      targetSource.put(DataSourceType.ILLEGAL_DETAIL, illegalDetailDataSource);
//
//      MyBeanDataSource myBeanDataSource = new MyBeanDataSource();
//      myBeanDataSource.setTargetDataSources(targetSource);
//      myBeanDataSource.setDefaultTargetDataSource(examineDataSource);
//
//      return myBeanDataSource;
//    }
  }


}