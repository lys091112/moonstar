package com.github.springboot.support.springusetest;

import com.github.springboot.support.springusetest.retrofit.SpecialComponentProvider;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author Xianyue
 * 控制台输出顺序： initializing bean --> postConstruct bean --> after propertiesSet
 */
public class InnitialzingBeanTest implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {

    private BeanFactory beanFactory;
    private String beanName;

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

    public void myInit() {
        System.out.println("--------my init--------");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destory bean");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("---------set bean factory");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("-------set bean name------------");
        this.beanName = name;
    }
}
