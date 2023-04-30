package com.yanzhen.dao;

import com.yanzhen.model.RoomType;

import java.sql.SQLException;
import java.util.List;

public interface TypeInfoDao {
    /**
     * 查询房间类型列表
     * @param startPage 起始页
     * @param limit  每页显示的记录数据
     * @param content  高级查询的条件
     * @return
     */
     List<RoomType> queryList(int startPage,int limit,String content) throws SQLException;


    /**
     * 获取总的记录数据
     */
    int getCounts(String content) throws SQLException;

    /**
     * 增加类型
     */
    boolean saveInfo(RoomType type);

    /**
     * 删除
     */
    boolean deleteById(Integer id);

    /**
     * 根据id查询对象
     */
    RoomType queryByID(Integer id) throws SQLException;

    /**
     * 修改提交操作
     */
    boolean updateInfo(RoomType type);
}
