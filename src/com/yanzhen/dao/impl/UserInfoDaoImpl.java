package com.yanzhen.dao.impl;

import com.yanzhen.dao.UserInfoDao;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.model.UserInfo;
import com.yanzhen.util.DateUtil;
import com.yanzhen.util.DbUtils;
import com.yanzhen.util.JdbcUtil;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class UserInfoDaoImpl implements UserInfoDao {
    DbUtils utils=new DbUtils();
    @Override
    public List<UserInfo> queryList(int startPage, int limit, String username) throws SQLException {
        String sql="select * from admin where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        if(username!=null){
            username="%"+username+"%";
            sb.append( " and username like '"+username+"'");
        }
        sb.append(" limit "+startPage+","+limit);
        List<UserInfo> list=utils.list(UserInfo.class,sb.toString(),null);
        return list;
    }

    @Override
    public int getCounts(String username) throws SQLException {
        String sql="select * from admin where 1=1 ";
        StringBuffer sb=new StringBuffer(sql);
        if(username!=null){
            username="%"+username+"%";
            sb.append( " and username like '"+username+"'");
        }
        int num=utils.getCount(sql);
        return num;
    }

    @Override
    public boolean saveInfo(UserInfo info) {
        String date= DateUtil.dateChangeString(new Date());
        String sql="insert into admin " +
                "(nickname,username,identity,tel,email,create_time,sex,type,password,create_author) values(" +
                " '"+info.getNickname()+"','"+info.getUsername()+"','"+info.getIdentity()+"'," +
                " '"+info.getTel()+"'," +
                " '"+info.getEmail()+"','"+date+"','"+info.getSex()+"',"+info.getType()+",'"+info.getPassword()+"'," +
                " '"+info.getCreate_author()+"')";
        int nums= JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql="delete from admin where id="+id;
        int nums=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public UserInfo queryByID(Integer id) throws SQLException {
        String sql="select * from admin where id="+id;
        UserInfo info=utils.query(UserInfo.class,sql,null);
        return info;
    }

    @Override
    public boolean updateInfo(UserInfo type) {
        String sql="update admin set nickname='"+type.getNickname()+"'," +
                " username='"+type.getUsername()+"'," +
                " tel='"+type.getTel()+"',"+
                " email='"+type.getEmail()+"' where id="+type.getId();
        int nums=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePasswordByUserId(String password,Integer userID) {
        String sql="update admin set password='"+password+"' where id="+userID;
        int nums=JdbcUtil.insertOrUpdateOrDeleteInfo(sql);
        if(nums>0){
            return true;
        }
        return false;
    }

    @Override
    public UserInfo queryUserNameAndPwd(String username, String password) throws SQLException {
        String sql="select * from admin where " +
                "username='"+username+"' and password='"+password+"'";
        ResultSet  rs=JdbcUtil.querySql(sql);
        UserInfo userInfo = null;
        if(rs.next()){
            userInfo = new UserInfo();
             userInfo.setUsername(rs.getString("username"));
             userInfo.setPassword(rs.getString("password"));
             userInfo.setEmail(rs.getString("email"));
        }
        return userInfo;
    }
}
