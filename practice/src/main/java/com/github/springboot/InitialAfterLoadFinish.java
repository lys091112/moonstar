package com.github.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 自定义加载完成后的执行事件
 * 例如有些操作依赖初始化时的某些类，那么必须要保证该类初始化后执行该方法，此时可以使用该方式
 */
@Slf4j
public class InitialAfterLoadFinish implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("======================");
        log.info("spring 初始化完成");
        log.info("======================");
        // do you own job
    }
}
