package com.peaceful.community.service;

import com.peaceful.community.dao.UserMapper;
import com.peaceful.community.domain.User;
import com.peaceful.community.util.CommunityUtil;
import com.peaceful.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailClient mailClient; // 邮件客户端
    @Autowired
    private TemplateEngine templateEngine; // 模板引擎
    /*
    *   发送邮件时，要生成一个激活码，激活码中包含域名和项目名
    * */
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.contextPath}")
    private String contextPath;
    // 根据用户di查询用户的方法
    public User findUserById(int id){
        return userMapper.selectById(id);
    }

    /**
     *  进行注册方法
     * @param user
     * @return
     */
    public Map<String, Object> register(User user){
        Map<String, Object> map = new HashMap<>();
        // 对传入user注册信息的相关处理
        if(user == null)
            throw  new IllegalArgumentException("参数不能为空");
        if(StringUtils.isBlank((user.getUsername()))){
            map.put("usernameMsg", "账号不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg", "邮箱不能为空！");
            return map;
        }

        // 验证账号是否已存在
        User u = userMapper.selectByName(user.getUsername());
        if(u != null){  // 账号存在
            map.put("usernameMsg", "账号已存在！");
            return map;
        }
        // 验证邮箱是否已存在
        u = userMapper.selectByEmail(user.getEmail());
        if(u != null){
            map.put("emailMsg", "邮箱已存在！");
            return map;
        }

        // 注册用户
        // 保存前对密码进行加密
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.MD5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        // 随机头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreatTime(new Date());
        // 将用户添加到数据库中,mybatis生成id回填
        userMapper.insertUser(user);

        // 给用户发送激活邮件
        Context context = new Context();
        context.setVariable("emial", user.getEmail());
        // url指向激活页面
        // http://localhost:8080/community/activation/101/code
        String url = domain+contextPath+"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url", url);
        String c = templateEngine.process("/mail/activation", context);
        // 发送邮件
        mailClient.sendMail(user.getEmail(), "激活账号", c);

        return map; // 空的，表示没有问题
    }


}
