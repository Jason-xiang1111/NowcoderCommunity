package com.peaceful.community.dao;

import com.peaceful.community.domain.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 个人主页帖子查询
    // 首页上userid==0，个人主页上userid!=0
    // 分页
    List<DiscussPost> selectDiscussPosts(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    // 查询所有帖子
    // @Param()给参数取一个别名
    // 在动态sql中需要使用的条件，并且这个方法有且只有一个参数必须加@Param
    // 若只有一个参数，并且在<if>中使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    // 增加帖子的方法
    int insertDiscussPost(DiscussPost discussPost);

    // 查询帖子详情
    DiscussPost selectDiscussPostById(int id);

    // 更新评论数量
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
