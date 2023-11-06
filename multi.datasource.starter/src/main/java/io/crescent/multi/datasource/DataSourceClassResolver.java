package io.crescent.multi.datasource;

import io.crescent.multi.datasource.annotation.DS;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ClassUtils;

/**
 * @author crescent
 */
public class DataSourceClassResolver {

  private Map<Object, String> dsCache = new ConcurrentHashMap<>();
  static boolean hasMybatis = false;
  private static Field field;

  static {
    Class<?> proxyClass = null;
    try {
      proxyClass = Class.forName("org.apache.ibatis.binding.MapperProxy");
    } catch (ClassNotFoundException e) {
    }
    if (proxyClass != null) {
      try {
        field = proxyClass.getField("mapperInterface");
        field.setAccessible(true);
        hasMybatis = true;
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
  }


  public String findKey(Method method, Object targetObject) {
    if (method.getDeclaringClass() == Object.class) {
      return "";
    }

    MethodClassKey methodClassKey = new MethodClassKey(method, targetObject.getClass());
    String ds = dsCache.get(methodClassKey);
    if (null == ds) {
      // 获取方法上的主机
      ds = findDataSource(method, targetObject);
      if (ds == null) {
        ds = "";
      }
      dsCache.put(methodClassKey, ds);
    }
    return ds;
  }

  private String findDataSource(Method method, Object targetObject) {
    // 原生方法
    String key = findDataSourceAttribute(method);
    if (null != key) {
      return key;
    }

    // 桥接方法
    Class targetClass = targetObject.getClass();
    Class<?> userClass = ClassUtils.getUserClass(targetClass);
    // JDK代理时,  获取实现类的方法声明.  method: 接口的方法, specificMethod: 实现类方法
    Method specificMethod = ClassUtils.getMostSpecificMethod(method, userClass);

    specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
    key = findDataSourceAttribute(specificMethod);
    if (null != key) {
      return key;
    }

    // 从当前类中获取
    key = findDataSourceAttribute(userClass);
    if (key != null) {
      return key;
    }

    // 从接口查找，只取第一个找到的
    for (Class<?> interfaceClazz : ClassUtils.getAllInterfacesForClassAsSet(userClass)) {
      if ((key = findDataSourceAttribute(interfaceClazz)) != null) {
        return key;
      }
    }

    return getDefaultDataSourceAttr(targetObject);
  }

  private String getDefaultDataSourceAttr(Object targetObject) {
    if (targetObject.getClass() == Object.class) {
      return null;
    }

    if (hasMybatis) {
      final Class<?> clazz = getMapperInterfaceClass(targetObject);
      if (clazz != null) {
        String key = findDataSourceAttribute(clazz);
        if (key != null) {
          return key;
        }
        // 尝试从其父接口获取
        return findDataSourceAttribute(clazz.getSuperclass());
      }
    }
    return null;
  }

  private Class<?> getMapperInterfaceClass(Object target) {
    Object current = target;
    while (Proxy.isProxyClass(current.getClass())) {
      Object currentRefObject = AopProxyUtils.getSingletonTarget(current);
      if (currentRefObject == null) {
        break;
      }
      current = currentRefObject;
    }
    try {
      if (Proxy.isProxyClass(current.getClass())) {
        return (Class<?>) field.get(Proxy.getInvocationHandler(current));
      }
    } catch (IllegalAccessException ignore) {
    }
    return null;


  }

  /**
   * 通过 AnnotatedElement 查找标记的注解, 映射为  DatasourceHolder
   *
   * @param ae AnnotatedElement
   * @return 数据源映射持有者
   */
  private String findDataSourceAttribute(AnnotatedElement ae) {
    AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ae, DS.class);
    if (attributes != null) {
      String key = attributes.getString("value");
      DataSourceType type = attributes.getEnum("type");
      if (null == type) {
        return DataSourceType.NORMAL.getDsKey(key);
      }
      return type.getDsKey(key);
    }
    return null;
  }

}
