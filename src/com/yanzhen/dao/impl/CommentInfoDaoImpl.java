package com.yanzhen.dao.impl;

import com.yanzhen.dao.CommentInfoDao;
import com.yanzhen.model.CommentInfo;
import com.yanzhen.util.DateUtil;
import com.yanzhen.util.DbUtils;
import com.yanzhen.util.JdbcUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentInfoDaoImpl implements CommentInfoDao {

    @Override
    public List<CommentInfo> queryList(int startPage, int limit, String content) throws SQLException {
        List<CommentInfo> list=new ArrayList<>();
        String sql="select com.*,info.number from room_comment com " +
                " LEFT JOIN room_info info on info.id=com.room_id where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append( " and number like '"+content+"'");
        }
        sb.append(" limit "+startPage+","+limit);
        ResultSet rs=JdbcUtil.querySql(sb.toString());
        while(rs.next()){
            CommentInfo info=new CommentInfo();
            info.setId(rs.getInt("id"));
            info.setCommentTime(rs.getDate("comment_time"));
            info.setEmail(rs.getString("email"));
            info.setNumber(rs.getString("number"));
            info.setRemark(rs.getString("remark"));
            info.setRoomId(rs.getInt("room_id"));
            info.setUsername(rs.getString("username"));
            list.add(info);
        }
        return  list;
    }

    @Override
    public int getCounts(String content) throws SQLException {
        List<CommentInfo> list=new ArrayList<>();
        String sql="select com.*,info.number from room_comment com " +
                " LEFT JOIN room_info info on info.id=com.room_id where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append( " and number like '"+content+"'");
        }
        int num=new DbUtils().getCount(sb.toString());
        return num;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql="delete from room_comment where id="+id;
        int num=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(num>0){
            return true;
        }
        return false;
    }

    @Override
    public List<CommentInfo> queryListByRoomID(Integer roomId) throws SQLException {
        List<CommentInfo> list=new ArrayList<>();
        String sql="select * from room_comment where room_id="+roomId;
        ResultSet rs=JdbcUtil.querySql(sql);
        while(rs.next()){
            CommentInfo info=new CommentInfo();
            info.setId(rs.getInt("id"));
            info.setCommentTime(rs.getDate("comment_time"));
            info.setRemark(rs.getString("remark"));
            info.setUsername(rs.getString("username"));
            list.add(info);
        }
        return  list;
    }

    @Override
    public boolean saveInfo(CommentInfo info) {
        String date= DateUtil.dateChangeString(new Date());
        String sql="insert into room_comment ( room_id,comment_time," +
                " username,email,remark ) " +
                " values ( "+info.getRoomId()+",'"+date+"'," +
                " '"+info.getUsername()+"','"+info.getEmail()+"'," +
                " '"+info.getRemark()+"')";
        int num=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(num>0){
            return true;
        }
        return false;
    }
}
