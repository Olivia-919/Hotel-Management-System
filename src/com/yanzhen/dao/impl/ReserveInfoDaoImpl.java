package com.yanzhen.dao.impl;

import com.yanzhen.dao.ReserveInfoDao;
import com.yanzhen.model.ReserveInfo;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.util.DbUtils;
import com.yanzhen.util.JdbcUtil;

import javax.sql.rowset.JdbcRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReserveInfoDaoImpl implements ReserveInfoDao {
    DbUtils utils=new DbUtils();
    @Override
    public List<ReserveInfo> queryList(int startPage, int limit, String content) throws SQLException {
        String sql="select info.* ,room.number   from reserve_info info " +
                " LEFT JOIN room_info room on room.id=info.room_id where  info.state !=2 ";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append( " and id_card like '"+content+"'");
        }
        sb.append(" order by id desc");
        sb.append(" limit "+startPage+","+limit );
        List<ReserveInfo> list=utils.list(ReserveInfo.class,sb.toString(),null);
        return list;
    }

    @Override
    public int getCounts(String content) throws SQLException {
        String sql="select info.* ,room.number   from reserve_info info " +
                " LEFT JOIN room_info room on room.id=info.room_id where  info.state !=2 ";
        StringBuffer sb=new StringBuffer(sql);
        if(content!=null){
            content="%"+content+"%";
            sb.append( " and id_card like '"+content+"'");
        }
       return  utils.getCount(sb.toString());
    }

    @Override
    public boolean saveInfo(ReserveInfo info) {
        boolean bs=utils.save("insert into reserve_info (room_id,id_card,tel,counts," +
                " start_time,end_time,is_pay,state) values (?,?,?,?,?,?,?,?)",
                info.getRoom_id(),info.getId_card(),info.getTel(),info.getCounts(),
                info.getStart_time(),info.getEnd_time(),info.getIs_pay(),info.getState());
        return bs;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql="delete from reserve_info where id="+id;
        return utils.remove(sql,null);
    }

    @Override
    public ReserveInfo queryByID(Integer id) throws SQLException {
        String sql="select * from reserve_info where id="+id;
        ReserveInfo info=utils.query(ReserveInfo.class,sql,null);
        return info;
    }

    @Override
    public boolean updateInfo(ReserveInfo info) {
        boolean bs=utils.update("update reserve_info set tel=?," +
                " start_time=?," +
                " end_time=?,state=? where id="+info.getId(),
                 info.getTel(),info.getStart_time(),info.getEnd_time(),info.getState());
        return bs;
    }

    @Override
    public boolean updateStatusById(Integer status, Integer id) {
       boolean bs= utils.update("update reserve_info set state=? where id="+id,status);
       return bs;
    }

    @Override
    public List<RoomInfo> queryListByUserCradAndTel(String idcard, String tel) throws SQLException {
        String sql="select room.id,room.number from reserve_info info " +
                " LEFT JOIN room_info room on room.id=info.room_id " +
                " where info.state=1 and id_card='"+idcard+"' " +
                " and tel='"+tel+"'";
        ResultSet rs=JdbcUtil.querySql(sql);
        List<RoomInfo> list=new ArrayList<>();
        while(rs.next()){
            RoomInfo info=new RoomInfo();
            info.setId(rs.getInt("id"));
            info.setNumber(rs.getString("number"));
            list.add(info);
        }
        return list;
    }
}
