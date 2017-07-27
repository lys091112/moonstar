package com.xianyue.retrofit.autoconfig;

import com.xianyue.retrofit.RetrofitCompnontScan;
import org.springframework.context.annotation.Configuration;

/**
 * retrofit 自动加载类
 */
@Configuration
@RetrofitCompnontScan("com.xianyue.retrofit")
public class RetrofitAutoConfiguration {

}
