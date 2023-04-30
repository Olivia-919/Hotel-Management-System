package com.yanzhen.service.impl;

import com.yanzhen.dao.CommentInfoDao;
import com.yanzhen.dao.impl.CommentInfoDaoImpl;
import com.yanzhen.model.CommentInfo;
import com.yanzhen.model.UserInfo;
import com.yanzhen.service.CommentInfoService;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;
import java.util.List;

public class CommentInfoServiceImpl implements CommentInfoService {
    CommentInfoDao dao=new CommentInfoDaoImpl();
    @Override
    public PageBean getPage(String page, String limit, String username) throws SQLException {
        //引入分页对象
        PageBean<CommentInfo> pageBean=new PageBean<>();
        //当前页
        Integer page2=Integer.parseInt(page);
        //每页显示记录数
        Integer limit2=Integer.parseInt(limit);
        //起始记录数  类似
        int startPage=(page2-1)*pageBean.getPageCount();
        //总的记录数据
        int counts=dao.getCounts(username);
        //获取list  开始记录
        List<CommentInfo> list= dao.queryList(startPage,limit2,username);
        //设置对象
        pageBean.setPageData(list);
        //总的数据
        pageBean.setTotalCount(counts);
        //总的页数
        pageBean.setTotalPage(counts/pageBean.getPageCount()+1);
        return pageBean;
    }

    @Override
    public boolean deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public List<CommentInfo> queryListByRoomID(Integer roomId) throws SQLException {
        return dao.queryListByRoomID(roomId);
    }

    @Override
    public boolean saveInfo(CommentInfo info) {
        return dao.saveInfo(info);
    }
}
