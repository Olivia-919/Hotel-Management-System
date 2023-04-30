package com.yanzhen.service.impl;

import com.yanzhen.dao.UserInfoDao;
import com.yanzhen.dao.impl.UserInfoDaoImpl;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.model.UserInfo;
import com.yanzhen.service.UserInfoService;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;
import java.util.List;

public class UserInfoServiceImpl implements UserInfoService {
    UserInfoDao dao=new UserInfoDaoImpl();
    @Override
    public PageBean getPage(String page, String limit, String username) throws SQLException {
        //引入分页对象
        PageBean<UserInfo> pageBean=new PageBean<>();
        //当前页
        Integer page2=Integer.parseInt(page);
        //每页显示记录数
        Integer limit2=Integer.parseInt(limit);
        //起始记录数  类似
        int startPage=(page2-1)*pageBean.getPageCount();
        //总的记录数据
        int counts=dao.getCounts(username);
        //获取list  开始记录
        List<UserInfo> list= dao.queryList(startPage,limit2,username);
        //设置对象
        pageBean.setPageData(list);
        //总的数据
        pageBean.setTotalCount(counts);
        //总的页数
        pageBean.setTotalPage(counts/pageBean.getPageCount()+1);
        return pageBean;
    }



    @Override
    public boolean saveInfo(UserInfo type) {
        return dao.saveInfo(type);
    }

    @Override
    public boolean deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public UserInfo queryByID(Integer id) throws SQLException {
        return dao.queryByID(id);
    }

    @Override
    public boolean updateInfo(UserInfo type) {
        return dao.updateInfo(type);
    }

    @Override
    public boolean updatePasswordByUserId(String password, Integer userID) {
        return dao.updatePasswordByUserId(password,userID);
    }

    @Override
    public UserInfo queryUserNameAndPwd(String username, String password) throws SQLException {
        return dao.queryUserNameAndPwd(username,password);
    }
}
