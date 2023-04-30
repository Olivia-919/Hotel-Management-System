package com.yanzhen.dao;

import com.yanzhen.model.ReserveInfo;
import com.yanzhen.model.RoomInfo;

import java.sql.SQLException;
import java.util.List;

public interface ReserveInfoDao {
    /**
     * 分页查询
     * @param startPage 开始页
     * @param limit 每页显示记录数
     * @param content 高级查询参数
     * @return
     * @throws SQLException
     */
    List<ReserveInfo> queryList(int startPage, int limit, String content) throws SQLException;

    /**
     * 获取总的记录数据
     */
    int getCounts(String content) throws SQLException;

    /**
     * 增加类型
     */
    boolean saveInfo(ReserveInfo reserveInfo);

    /**
     * 删除
     */
    boolean deleteById(Integer id);

    /**
     * 根据id查询对象
     */
    ReserveInfo queryByID(Integer id) throws SQLException;

    /**
     * 修改提交操作
     */
    boolean updateInfo(ReserveInfo reserveInfo);

    /**
     * 审核状态
     * 根据id修改状态 预定信息的id
     */
     boolean updateStatusById(Integer status,Integer id);

    /**
     * 根据用户的身份证号和电话号码查询记录信息
     */
    List<RoomInfo> queryListByUserCradAndTel(String idcard, String tel) throws SQLException;

}
