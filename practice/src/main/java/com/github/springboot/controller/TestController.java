package com.github.springboot.controller;

import com.github.springboot.support.interceptor.permission.Permission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/")
@RestController
public class TestController {


    @RequestMapping(value = "normal")
    public String normal() {
        return "normal";
    }

    @Permission
    @RequestMapping(value = "permissiondeny")
    public String permissionDeny() {
        return "permission_deny";
    }

    @RequestMapping(value = "cache")
    @Cacheable(cacheNames = "foo2", key = "'list'", sync = true)
    public String cache() {
        return "cache";
    }
}
