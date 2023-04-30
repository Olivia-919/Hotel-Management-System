package com.yanzhen.dao;

import com.yanzhen.model.CheckOutRoom;

import java.sql.SQLException;
import java.util.List;

public interface CheckOutDao {
    List<CheckOutRoom> queryList(int startPage, int limit, String content) throws SQLException;

    /**
     * 获取总的记录数据
     */
    int getCounts(String content) throws SQLException;

    /**
     * 增加
     */
    boolean saveInfo(CheckOutRoom type);

    /**
     * 删除
     */
    boolean deleteById(Integer id);

    /**
     * 根据id查询对象
     */
    CheckOutRoom queryByID(Integer id) throws SQLException;
}
