package com.github.springboot.rule;

import lombok.Getter;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogger implements TestRule {
    @Getter
    private Logger logger;


    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println("hello");
                System.out.println(description.getTestClass().getName());
                System.out.println(description.getDisplayName());
                logger = LoggerFactory.getLogger(description.getTestClass().getName() + "."
                + description.getDisplayName());
                base.evaluate();
                System.out.println("end");
            }
        };
    }
}
