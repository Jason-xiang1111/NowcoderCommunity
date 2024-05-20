package com.peaceful.community.dao;

import com.peaceful.community.domain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    // from_id到查询一页消息的方法，每个会话显示最新的私信
    List<Message> selectConversations(@Param("userId") int userId, @Param("offset") int offset,@Param("limit") int limit);

    // from_id到查询总行数消息方法
    int selectConversationCount(@Param("userId")int userId);

    // from-to的具体消息列表
    List<Message> selectLetters(@Param("conversationId") String conversationId,@Param("offset") int offset,@Param("limit") int limit);

    // from-to的具体消息条数
    int selectLetterCount(@Param("conversationId") String conversationId);

    // from_id的未读消息数量，复用该方法
    // 总数是依靠userId，每个消息的总数一开conversationId
    int selectLetterUnreadCount(@Param("userId")int userId,@Param("conversationId") String conversationId);

    // 新增消息
    int insertMessage(Message message);

    // 修改读取状态
    int updateStatus(@Param("ids") List<Integer> ids, @Param("status") int status);

}
