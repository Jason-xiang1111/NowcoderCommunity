package com.peaceful.community.dao;

import com.peaceful.community.domain.LoginTicket;
import org.apache.ibatis.annotations.*;

import javax.swing.*;

/*
*   对ticket表的逻辑处理
*   声明bean，装配至spring容器中
* */
@Mapper
public interface LoginTicketMapper {

    @Insert({
            "insert into login_ticket(user_id, ticket, status, expired) ",
            "values (#{userId}, #{ticket}, #{status}, #{expired})"
    }) // 自动拼接
    @Options(useGeneratedKeys = true, keyProperty = "id") //自动生成主键
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket= #{ticket}"
    })
    LoginTicket selectByTicket(String ticket);
    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket}"
    })
    int updateStatus(@Param("ticket") String ticket, @Param("status") int status);

}
