package com.peaceful.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/*
*   提供发邮件的功能
*   代替了邮箱的客户端向其他邮箱发送邮件
* */
// 通用的bean，在哪个层次都可以使用
@Component
public class MailClient {
    // 记录日志
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from; // 发件人

    /**
     *  功能：发送邮件
     */
    public void sendMail(String to, String subject, String content){

        try {
            // 构建MimeMessage,空对象
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            // 发件人
            helper.setFrom(from);
            // 收件人
            helper.setTo(to);
            helper.setSubject(subject);
            // 无第二个参数，默认普通文本
            helper.setText(content, true); // 加参支持html文件

            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败"+e.getMessage());
        }
    }

}
