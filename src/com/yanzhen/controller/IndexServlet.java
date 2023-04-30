package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.service.ReserveInfoService;
import com.yanzhen.service.RoomInfoService;
import com.yanzhen.service.impl.ReserveInfoServiceImpl;
import com.yanzhen.service.impl.RoomInfoServiceImpl;
import com.yanzhen.util.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/indexServlet")
public class IndexServlet extends HttpServlet {
    ReserveInfoService service=new ReserveInfoServiceImpl();
    RoomInfoService roomInfoService=new RoomInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
     switch (flag){
         case "query":
             String idcrad=req.getParameter("idCard");
             String tel=req.getParameter("tel");
             resp.setContentType("application/json;chartset=utf-8");
             try {
                 List<RoomInfo> list=service.queryListByUserCradAndTel(idcrad,tel);
                 if(list.size()>0){
                     String str="您预定的房间是：";
                     for(RoomInfo info:list){
                         str+=info.getNumber()+",";
                     }
                     resp.getWriter().print(JSON.toJSONString(R.ok(str,list)));
                 }else{
                     resp.getWriter().print(JSON.toJSONString(R.fail("不好意思，没预定信息")));
                 }
             } catch (SQLException throwables) {
                 throwables.printStackTrace();
             }
             break;
         default:
             query(req,resp);
             break;
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
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
