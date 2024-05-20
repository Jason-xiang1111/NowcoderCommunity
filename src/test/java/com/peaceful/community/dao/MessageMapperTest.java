package com.peaceful.community.dao;

import com.peaceful.community.domain.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MessageMapperTest {

    @Autowired
    private MessageMapper messageMapper;

    @Test
    public void testMessage(){
        List<Message> messages = messageMapper.selectConversations(111, 0, 25);
        for (Message message : messages) {
            System.out.println(message);
        }

        int cnt = messageMapper.selectConversationCount(111);
        System.out.println(cnt);

        messages = messageMapper.selectLetters("111_112", 0, 15);
        for (Message message : messages) {
            System.out.println(message);
        }

        cnt = messageMapper.selectLetterCount("111_112");
        System.out.println(cnt);

        int count = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count);
    }
}
