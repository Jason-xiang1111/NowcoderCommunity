package com.peaceful.community.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@SpringBootTest
public class MailTest {
    @Autowired
    private MailClient mailClient;

    // templeaf模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("1198279536@qq.com", "test", "welcome");
    }

    @Test
    public void testHtmlMail(){
        // 访问模板，向模板传参
        Context context = new Context();
        context.setVariable("username", "sunday");

        // 调模板引擎
       String con =  templateEngine.process("/mail/demo", context);
        System.out.println(con);

        mailClient.sendMail("peaceful_x@foxmail.com", "html", con);
    }


}
