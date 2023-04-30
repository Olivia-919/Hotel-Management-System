package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.CommentInfo;
import com.yanzhen.model.ReserveInfo;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.service.CommentInfoService;
import com.yanzhen.service.ReserveInfoService;
import com.yanzhen.service.RoomInfoService;
import com.yanzhen.service.impl.CommentInfoServiceImpl;
import com.yanzhen.service.impl.ReserveInfoServiceImpl;
import com.yanzhen.service.impl.RoomInfoServiceImpl;
import com.yanzhen.util.DateUtil;
import com.yanzhen.util.R;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/reserveWebServlet")
public class ReserveWebServlet extends HttpServlet {
    RoomInfoService roomInfoService =new RoomInfoServiceImpl();
    ReserveInfoService service=new ReserveInfoServiceImpl();
    CommentInfoService commentInfoService=new CommentInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
        switch (flag){
            case "queryById":
                //根据id查询详细信息
                String id=req.getParameter("id");
                try {
                    RoomInfo info=roomInfoService.queryByID(Integer.parseInt(id));
                    req.setAttribute("info",info);
                    req.getRequestDispatcher("details.jsp").forward(req,resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            case "saveInfo":
                ReserveInfo info= null;
                try {
                    info = getValues(req);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                service.saveInfo(info);
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
            case "commentList":
                queryListByRoomId(req,resp);
                break;
            case "saveComment":
                String roomId=req.getParameter("roomId")==null?null:req.getParameter("roomId");
                String username=req.getParameter("username");
                String email=req.getParameter("email");
                String remark=req.getParameter("remark");
                CommentInfo commentInfo=new CommentInfo();
                commentInfo.setUsername(username);
                if(roomId!=null){
                    commentInfo.setRoomId(Integer.parseInt(roomId));
                }
                commentInfo.setRemark(remark);
                commentInfo.setEmail(email);
                commentInfoService.saveInfo(commentInfo);
                if(roomId!=null){//不是空是评论信息
                    queryListByRoomId(req,resp);
                }else{
                    resp.setContentType("application/json;chartset=utf-8");
                    resp.getWriter().print(JSON.toJSONString(R.ok()));
                }
                break;
            default:
                query(req,resp);
                break;
        }
    }


    /**
     * 查询评论信息根据房间id
     */
    public void queryListByRoomId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomID=req.getParameter("roomId");
        try {
            List<CommentInfo> list=   commentInfoService.queryListByRoomID(Integer.parseInt(roomID));
            resp.setContentType("application/json;chartset=utf-8");
            Map map=new HashMap();
            map.put("list",list);
            String jsons= JSON.toJSONString(map);
            resp.getWriter().print(jsons);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 查询主页中的房间列表信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<RoomInfo> list=roomInfoService.queryRoomListByStatus(0);
        req.setAttribute("list",list);
        req.getRequestDispatcher("room.jsp").forward(req,resp);
    }



    /**
     * 根据前端传值获取对应的值信息，并把值信息封装到RoomInfo 对象中
     * @param req
     * @return
     */
    public ReserveInfo getValues(HttpServletRequest req) throws ParseException {
        //根据id判断是修改还新增，如修改id不为空
        ReserveInfo info=new ReserveInfo();
        String room_id=req.getParameter("room_id");
        String id_card=req.getParameter("idCard");
        String tel=req.getParameter("tel");
        String start_time=req.getParameter("startTime");
        String end_time=req.getParameter("endTime");
        String state=req.getParameter("state")==null?"0":req.getParameter("state");
        String is_pay=req.getParameter("isPay")==null?"0":req.getParameter("isPay");
        info.setRoom_id(Integer.parseInt(room_id));
        info.setId_card(id_card);
        info.setTel(tel);
        info.setCounts(1);
        info.setStart_time(DateUtil.stringToDate(start_time));
        info.setEnd_time(DateUtil.stringToDate(end_time));
        info.setState(Integer.parseInt(state));
        info.setIs_pay(Integer.parseInt(is_pay));
        return info;
    }

}
