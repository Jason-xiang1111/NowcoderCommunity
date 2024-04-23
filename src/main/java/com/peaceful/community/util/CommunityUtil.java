package com.peaceful.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    // 生成随机字符串（用于激活码）
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密：只能加密，不能解密
    // 采用md5算法，对密码进行加密
    // 对密码加上随机字符串再进行加密
    // key就是密码+随机字符串
    public static String MD5(String key){
        /*Spring自带md5算法的封装*/
        // 对字符串进行判空处理,key=null | "" | ' '都是空的
        // 用户的salt字段
        if(StringUtils.isBlank(key)){
            return null;
        }else {
            return DigestUtils.md5DigestAsHex(key.getBytes());
        }
    }
}
