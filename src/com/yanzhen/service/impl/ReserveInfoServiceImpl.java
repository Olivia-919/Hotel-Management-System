package com.yanzhen.service.impl;

import com.yanzhen.dao.ReserveInfoDao;
import com.yanzhen.dao.RoomInfoDao;
import com.yanzhen.dao.impl.ReserveInfoDaoImpl;
import com.yanzhen.dao.impl.RoomInfoDaoImpl;
import com.yanzhen.model.ReserveInfo;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.service.ReserveInfoService;
import com.yanzhen.util.PageBean;

import java.sql.SQLException;
import java.util.List;

public class ReserveInfoServiceImpl implements ReserveInfoService {
    ReserveInfoDao dao=new ReserveInfoDaoImpl();
    RoomInfoDao roomInfoDao=new RoomInfoDaoImpl();
    @Override
    public PageBean getPage(String page, String limit, String username) throws SQLException {
        //引入分页对象
        PageBean<ReserveInfo> pageBean=new PageBean<>();
        //当前页
        Integer page2=Integer.parseInt(page);
        //每页显示记录数
        Integer limit2=Integer.parseInt(limit);
        //起始记录数  类似
        int startPage=(page2-1)*pageBean.getPageCount();
        //总的记录数据
        int counts=dao.getCounts(username);
        //获取list  开始记录
        List<ReserveInfo> list= dao.queryList(startPage,limit2,username);
        //设置对象
        pageBean.setPageData(list);
        //总的数据
        pageBean.setTotalCount(counts);
        //总的页数
        pageBean.setTotalPage(counts/pageBean.getPageCount()+1);
        return pageBean;
    }

    @Override
    public boolean saveInfo(ReserveInfo type) {
        //在添加预定信息的时候需要更改房间的状态信息
        roomInfoDao.updateStatusById(1,type.getRoom_id());
        return dao.saveInfo(type);
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        //在添加预定信息的时候需要更改房间的状态信息
        Integer roomId=dao.queryByID(id).getRoom_id();
        roomInfoDao.updateStatusById(0,roomId);
        return dao.deleteById(id);
    }

    @Override
    public ReserveInfo queryByID(Integer id) throws SQLException {
        return dao.queryByID(id);
    }

    @Override
    public boolean updateInfo(ReserveInfo info) {
        return dao.updateInfo(info);
    }

    @Override
    public boolean updateStatus(Integer status, Integer id) {
        return dao.updateStatusById(status,id);
    }

    @Override
    public List<RoomInfo> queryListByUserCradAndTel(String idcard, String tel) throws SQLException {
        return dao.queryListByUserCradAndTel(idcard,tel);
    }
}
