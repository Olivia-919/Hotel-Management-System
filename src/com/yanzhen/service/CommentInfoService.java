package com.yanzhen.service;

import com.yanzhen.model.CommentInfo;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;
import java.util.List;

public interface CommentInfoService {

    /**
     * 分页查询
     */
    PageBean getPage(String page, String limit, String username) throws SQLException;

    /**
     * 删除
     */
    boolean deleteById(Integer id);

    /**
     * 根据房间id查询评论信息
     */
    List<CommentInfo> queryListByRoomID(Integer roomId) throws SQLException;


    /**
     * 前端评论提交
     */
    boolean saveInfo(CommentInfo info);
}
