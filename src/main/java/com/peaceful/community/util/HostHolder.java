package com.peaceful.community.util;

import com.peaceful.community.domain.User;
import org.springframework.stereotype.Component;

/*
*   持有用户信息，用来代替session对象
*
* */
@Component
public class HostHolder {
    // 线程隔离，set放值，get取值
    // 以线程为key存取值
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUsers(User user){
        users.set(user);
    }
    public User getUser(){
        return users.get();
    }
    public void clear(){
        users.remove();
    }
}
