package com.peaceful.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD) //// 拦截器拦截的是方法
@Retention(RetentionPolicy.RUNTIME) // 程序运行时有效
public @interface LoginRequired { // 标识作用
    // 打上这个标记，登录才能访问

}
