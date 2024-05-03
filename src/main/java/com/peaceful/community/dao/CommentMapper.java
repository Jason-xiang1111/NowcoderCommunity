package com.peaceful.community.dao;

import com.peaceful.community.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 查询评论，通过评论的类型，帖子的id
    // 同时进行分页处理,每页显示的行数限制
    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType, @Param("entityId")int entityId, @Param("offset")int offset,@Param("limit") int limit);

    // 查询评论数据条目数
    int selectCountByEntity(@Param("entityType") int entityType,@Param("entityId") int entityId);

    // 增加评论的方法
    int insertComment(Comment comment);


}
