package com.xianyue.retrofit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RetrofitCompnontScan("com.xianyue.retrofit")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class,args);
  }

}
