package com.github.springboot.domain;

import lombok.Getter;

/**
 *@author  XianYue
 */
@Getter
public class Greeting {

    private final int id;
    private final String context;

    public Greeting(int id, String context) {
        this.id = id;
        this.context = context;
    }
}
