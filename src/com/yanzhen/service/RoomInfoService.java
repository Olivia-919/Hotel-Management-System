package com.yanzhen.service;

import com.yanzhen.model.DataCount;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;
import java.util.List;

public interface RoomInfoService {

    /**
     * 分页查询
     */
    PageBean getPage(String page, String limit, String content) throws SQLException;

    /**
     * 增加
     */
    boolean saveInfo(RoomInfo info);

    /**
     * 删除
     */
    boolean deleteById(Integer id);

    /**
     * 根据id查询对象
     */
    RoomInfo queryByID(Integer id) throws SQLException;

    /**
     * 修改提交操作
     */
    boolean updateInfo(RoomInfo info);

    /**
     * 查询未预定房间信息
     */
    List<RoomInfo> queryListByStatus(Integer status);

    /**
     * 根据房间类型统计
     * @return
     */
    public List<DataCount> queryTjList() throws SQLException;


    /**
     * 根据状态查询房间信息 状态为 0
     * @return
     */
    List<RoomInfo> queryRoomListByStatus(Integer status);
}
