package com.yanzhen.service.impl;

import com.yanzhen.dao.TypeInfoDao;
import com.yanzhen.dao.impl.TypeInfoDaoImpl;
import com.yanzhen.model.RoomType;
import com.yanzhen.service.TypeInfoService;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;
import java.util.List;

public class TypeInfoServiceImpl implements TypeInfoService {
    TypeInfoDao dao=new TypeInfoDaoImpl();
    @Override
    public PageBean getPage(String page, String limit, String content) throws SQLException {
        //引入分页对象
        PageBean<RoomType> pageBean=new PageBean<>();
        //当前页
        Integer page2=Integer.parseInt(page);
        //每页显示记录数
        Integer limit2=Integer.parseInt(limit);
        //起始记录数  类似
        int startPage=(page2-1)*pageBean.getPageCount();
        //总的记录数据
        int counts=dao.getCounts(content);
        //获取list  开始记录
        List<RoomType> list= dao.queryList(startPage,limit2,content);
        //设置对象
        pageBean.setPageData(list);
        //总的数据
        pageBean.setTotalCount(counts);
        //总的页数
        pageBean.setTotalPage(counts/pageBean.getPageCount()+1);
        return pageBean;
    }

    @Override
    public boolean saveInfo(RoomType type) {
        return dao.saveInfo(type);
    }

    @Override
    public boolean deleteById(Integer id) {
        return dao.deleteById(id);
    }

    @Override
    public RoomType queryByID(Integer id) throws SQLException {
        return dao.queryByID(id);
    }

    @Override
    public boolean updateInfo(RoomType type) {
        return dao.updateInfo(type);
    }
}
