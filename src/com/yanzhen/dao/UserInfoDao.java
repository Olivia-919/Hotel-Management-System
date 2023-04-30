package com.yanzhen.dao;

import com.yanzhen.model.RoomType;
import com.yanzhen.model.UserInfo;

import java.sql.SQLException;
import java.util.List;

public interface UserInfoDao {
    List<UserInfo> queryList(int startPage, int limit, String content) throws SQLException;

    /**
     * 获取总的记录数据
     */
    int getCounts(String content) throws SQLException;

    /**
     * 增加类型
     */
    boolean saveInfo(UserInfo type);

    /**
     * 删除
     */
    boolean deleteById(Integer id);

    /**
     * 根据id查询对象
     */
    UserInfo queryByID(Integer id) throws SQLException;

    /**
     * 修改提交操作
     */
    boolean updateInfo(UserInfo type);

    /**
     * 根据用户修改密码
     * @param userID
     * @return
     */
    boolean updatePasswordByUserId(String password,Integer userID);

    /**
     * 根据用户名 密码返回用户对象
     */
    UserInfo queryUserNameAndPwd(String username,String password) throws SQLException;

}
