package com.peaceful.community.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.ws.Action;

@SpringBootTest
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public  void testSensitiveFilter(){
        String text = "这里可以赌博，可以吸毒，哈哈哈！";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
