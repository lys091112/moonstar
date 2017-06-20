package com.github.springboot.rule;

import org.junit.Rule;
import org.junit.Test;

public class MyLoggerTest {

    @Rule
    public final TestLogger testLogger = new TestLogger();

    @Test
    public void testMyLogger() {
//        final Logger logger = testLogger.getLogger();
//        logger.warn("my test own logger");
          System.out.println("hello");
    }
}
