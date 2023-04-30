package com.yanzhen.dao.impl;

import com.yanzhen.dao.RoomInfoDao;
import com.yanzhen.model.DataCount;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.util.DateUtil;
import com.yanzhen.util.DbUtils;
import com.yanzhen.util.JdbcUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomInfoDaoImpl implements RoomInfoDao {
    DbUtils utils=new DbUtils();

    @Override
    public List<RoomInfo> queryList(int startPage, int limit, String content) throws SQLException {
        String sql="select info.*,type_name from room_info info \n" +
                "left join room_type type on type.id=info.room_id where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append( " and number like '"+content+"'");
        }
        sb.append(" limit "+startPage+","+limit);
        List<RoomInfo> list=utils.list(RoomInfo.class,sb.toString(),null);
        return list;
    }

    @Override
    public int getCounts(String content) throws SQLException {
        String sql="select info.*,type_name from room_info info " +
                " left join room_type type on type.id=info.room_id";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append( " and number like '"+content+"'");
        }
        int num=utils.getCount(sb.toString());
        return num;
    }

    @Override
    public boolean saveInfo(RoomInfo info) {
        String date= DateUtil.dateChangeString(new Date());
        String sql="insert into room_info " +
                "(number,area,room_id,counts,bed,img,status,price,remark,create_time,create_author) values(" +
                " '"+info.getNumber()+"',"+info.getArea()+","+info.getRoom_id()+","+info.getCounts()+"," +
                ""+info.getBed()+",'"+info.getImg()+"',"+info.getStatus()+","+info.getPrice()+",'"+info.getRemark()+"'," +
                " '"+date+"','"+info.getCreate_author()+"')";
        int nums=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql="delete from room_info where id="+id;
        int nums=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public RoomInfo queryByID(Integer id) throws SQLException {
        String sql="select * from room_info where id="+id;
        RoomInfo info=utils.query(RoomInfo.class,sql,null);
        return info;
    }

    @Override
    public boolean updateInfo(RoomInfo info) {
        String sql="update room_info set area="+info.getArea()+"," +
                " room_id="+info.getRoom_id()+"," +
                " img='"+info.getImg()+"',status="+info.getStatus()+"," +
                " remark='"+info.getRemark()+"' where id="+info.getId();
        int nums=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStatusById(Integer status, Integer id) {
        String sql="update room_info set status ="+status+" where id="+id;
        int nums=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public List<RoomInfo> queryListByStatus(Integer status) {
        List<RoomInfo> list= utils.list(RoomInfo.class,"select id,number from room_info where status ="+status,null);
        return list;
    }

    @Override
    public List<DataCount> queryTjList() throws SQLException {
        List<DataCount> list=new ArrayList<>();
        String sql="select type.type_name,count(*) as counts from room_info  info " +
                " LEFT JOIN room_type type on type.id=info.room_id" +
                "  GROUP BY info.room_id ";
        ResultSet rs=JdbcUtil.querySql(sql);
        while(rs.next()){
            DataCount data=new DataCount();
            data.setName(rs.getString("type_name"));
            data.setCounts(rs.getInt("counts"));
            list.add(data);
        }
        return list;
    }

    @Override
    public List<RoomInfo> queryRoomListByStatus(Integer status) {
        String sql="select info.*,type_name  from room_info info " +
                " LEFT JOIN room_type type on type.id =info.room_id " +
                " where info.`status`=0";
        List<RoomInfo> list=utils.list(RoomInfo.class,sql,null);
        return list;
    }


}
