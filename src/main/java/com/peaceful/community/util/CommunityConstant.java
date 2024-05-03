package com.peaceful.community.util;

public interface CommunityConstant {
    /**
     *
     * 激活状态
     */
    int ACTIVATION_SUCCESS=0;  // 成功
    int ACTIVATION_RESET=1; // 重新激活
    int ACTIVATION_FAILURE=2; // 激活失败

    /**
     * 默认状态的登录凭证的超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    /**
     * 记住状态的登录凭证超时时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 12 * 100;

    /**
     *  实体类型：帖子
     */
    int ENTITY_TYPE_POST = 1;
    /**
     *  实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;


}
