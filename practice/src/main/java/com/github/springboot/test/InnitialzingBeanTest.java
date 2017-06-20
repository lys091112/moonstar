package com.github.springboot.test;

import com.github.springboot.test.retrofit.SpecialComponentProvider;
import java.util.Set;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Xianyue
 * 控制台输出顺序： initializing bean --> postConstruct bean --> after propertiesSet
 */
public class InnitialzingBeanTest implements InitializingBean, DisposableBean{

    public InnitialzingBeanTest() {
        System.out.println("----------------------->");
        System.out.println("initializing bean");
        System.out.println("----------------------->");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("after propertiesSet");
    }

    @PostConstruct
    public void init() {
        System.out.println("postConstruct init");
        Set<BeanDefinition> sets = SpecialComponentProvider.getInstance().findCandidateComponents("com.github");
        System.out.println("===========================>>>>>");
        sets.forEach(t -> System.out.print(t.getBeanClassName()));
        System.out.println();
        System.out.println("===========================>>>>>");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destory bean");
    }
}
