package com.yanzhen.service;

import com.yanzhen.model.ReserveInfo;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.model.UserInfo;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;
import java.util.List;

public interface ReserveInfoService {
    /**
     * 分页查询
     */
    PageBean getPage(String page, String limit, String username) throws SQLException;


    /**
     * 增加类型
     */
    boolean saveInfo(ReserveInfo type);

    /**
     * 删除
     */
    boolean deleteById(Integer id) throws SQLException;

    /**
     * 根据id查询对象
     */
    ReserveInfo queryByID(Integer id) throws SQLException;

    /**
     * 修改提交操作
     */
    boolean updateInfo(ReserveInfo info);

    /**
     * 修改状态
     */
    boolean updateStatus(Integer status,Integer id);

    /**
     * 根据用户的身份证号和电话号码查询记录信息
     */
    List<RoomInfo> queryListByUserCradAndTel(String idcard, String tel) throws SQLException;

}
