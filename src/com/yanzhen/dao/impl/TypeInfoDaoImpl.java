package com.yanzhen.dao.impl;


import com.yanzhen.dao.TypeInfoDao;
import com.yanzhen.model.RoomType;
import com.yanzhen.util.DateUtil;
import com.yanzhen.util.DbUtils;
import com.yanzhen.util.JdbcUtil;

import javax.sql.rowset.JdbcRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TypeInfoDaoImpl implements TypeInfoDao {
    DbUtils dbUtils=new DbUtils();
    @Override
    public List<RoomType> queryList(int startPage, int limit, String content) throws SQLException {
        List<RoomType> list=new ArrayList<RoomType>();
        String sql="select * from room_type where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append(" and type_name like '"+content+"'");
        }
        sb.append(" limit "+startPage+","+limit);
        ResultSet rs=JdbcUtil.querySql(sb.toString());
        while(rs.next()){
            RoomType type=new RoomType();
            type.setId(rs.getInt("id"));
            type.setTypeName(rs.getString("type_name"));
            type.setPrice(rs.getDouble("price"));
            type.setRemark(rs.getString("remark"));
            type.setCreateTime(rs.getDate("create_time"));
            type.setCreateAuthor(rs.getString("create_author"));
            list.add(type);
        }
        return list;
    }

    @Override
    public int getCounts(String content) throws SQLException {
        String sql="select * from room_type where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append(" and type_name like '"+content+"'");
        }
        int num=dbUtils.getCount(sb.toString());
        return num;
    }

    @Override
    public boolean saveInfo(RoomType type) {
        //把时间转成字符串
        String date= DateUtil.dateChangeString(new Date());
        String sql="insert into room_type(type_name,price,remark,create_time,create_author) values" +
                " ('"+type.getTypeName()+"',"+type.getPrice()+",'"+type.getRemark()+"'," +
                " '"+date+"','"+type.getCreateAuthor()+"')";
        int num= JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(num>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql="delete from room_type where id="+id;
        int num= JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(num>0){
            return true;
        }
        return false;
    }

    @Override
    public RoomType queryByID(Integer id) throws SQLException {
        String sql="select * from room_type where id="+id;
        ResultSet rs=JdbcUtil.querySql(sql);
        RoomType type=new RoomType();
        if(rs.next()){
            type.setId(rs.getInt("id"));
            type.setTypeName(rs.getString("type_name"));
            type.setPrice(rs.getDouble("price"));
            type.setRemark(rs.getString("remark"));
            type.setCreateTime(rs.getDate("create_time"));
            type.setCreateAuthor(rs.getString("create_author"));
        }
        return type;
    }

    @Override
    public boolean updateInfo(RoomType type) {
        String sql="update room_type set type_name='"+type.getTypeName()+"'," +
                " remark ='"+type.getRemark()+"' where id="+type.getId();
        int num=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(num>0){
            return true;
        }
        return false;
    }
}
