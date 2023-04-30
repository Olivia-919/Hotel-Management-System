package com.yanzhen.service;

import com.yanzhen.model.UserInfo;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;

public interface UserInfoService {

    /**
     * 分页查询
     */
    PageBean getPage(String page, String limit, String username) throws SQLException;


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
