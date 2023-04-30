package com.yanzhen.service.impl;

import com.yanzhen.dao.CheckOutDao;
import com.yanzhen.dao.ReserveInfoDao;
import com.yanzhen.dao.RoomInfoDao;
import com.yanzhen.dao.impl.CheckOutDaoImpl;
import com.yanzhen.dao.impl.ReserveInfoDaoImpl;
import com.yanzhen.dao.impl.RoomInfoDaoImpl;
import com.yanzhen.model.CheckOutRoom;
import com.yanzhen.model.ReserveInfo;
import com.yanzhen.model.RoomType;
import com.yanzhen.service.CheckOutService;
import com.yanzhen.util.PageBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CheckOutServiceImpl implements CheckOutService {
    CheckOutDao dao=new CheckOutDaoImpl();
    //创建类对象
    RoomInfoDao roomInfoDao=new RoomInfoDaoImpl();
    //引入 预定对象dao
    ReserveInfoDao reserveInfoDao=new ReserveInfoDaoImpl();
    @Override
    public PageBean getPage(String page, String limit, String content) throws SQLException {
        //引入分页对象
        PageBean<CheckOutRoom> pageBean=new PageBean<>();
        //当前页
        Integer page2=Integer.parseInt(page);
        //每页显示记录数
        Integer limit2=Integer.parseInt(limit);
        //起始记录数  类似
        int startPage=(page2-1)*pageBean.getPageCount();
        //总的记录数据
        int counts=dao.getCounts(content);
        //获取list  开始记录
        List<CheckOutRoom> list= dao.queryList(startPage,limit2,content);
        //设置对象
        pageBean.setPageData(list);
        //总的数据
        pageBean.setTotalCount(counts);
        //总的页数
        pageBean.setTotalPage(counts/pageBean.getPageCount()+1);
        return pageBean;
    }

    @Override
    public boolean saveInfo(CheckOutRoom outRoom) throws SQLException {
        /*
          1、退房信息添加
          2、房间状态的改变
          3、预定状态的改变
         */
        boolean  bs=dao.saveInfo(outRoom);
        //根据预定信息的id查询房间的id
        ReserveInfo reserveInfo=reserveInfoDao.queryByID(outRoom.getReserveId());
        //房间状态的改变  根据id改变房间状态
        roomInfoDao.updateStatusById(0,reserveInfo.getRoom_id());
        //预定状态的改变
        reserveInfoDao.updateStatusById(2,reserveInfo.getId());
        if(bs){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return dao.deleteById(id);

    }

    @Override
    public CheckOutRoom queryByID(Integer id) throws SQLException {
        return dao.queryByID(id);
    }
}
