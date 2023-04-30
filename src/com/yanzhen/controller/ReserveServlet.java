package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.ReserveInfo;
import com.yanzhen.model.UserInfo;
import com.yanzhen.service.ReserveInfoService;
import com.yanzhen.service.impl.ReserveInfoServiceImpl;
import com.yanzhen.util.DateUtil;
import com.yanzhen.util.JsonObject;
import com.yanzhen.util.PageBean;
import com.yanzhen.util.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/reserveServlet")
public class ReserveServlet extends HttpServlet {
    ReserveInfoService service=new ReserveInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        //接收前端传过来的功能标识
        String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
        switch (flag){
            case "insert":
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
            case "updateStatus":
                String id2=req.getParameter("id");
                String state=req.getParameter("state")==null?"1":req.getParameter("state");
                //如果是审核通过 默认为1
                service.updateStatus(Integer.parseInt(state),Integer.parseInt(id2));
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
            case "delete":
                String ids=req.getParameter("ids");
                //把接收到的字符串转成集合
                List<String> idList= Arrays.asList(ids.split(","));
                for(String id:idList){
                    try {
                        service.deleteById(Integer.parseInt(ids));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
            case "queryId":
                String id=req.getParameter("id");
                ReserveInfo info1= null;
                try {
                    info1 = service.queryByID(Integer.parseInt(id));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                req.setAttribute("info",info1);
                resp.setContentType("text/html;chartset=utf-8");
                req.getRequestDispatcher("admin/reserve/update.jsp").forward(req,resp);
                break;
            case "update":
                ReserveInfo roomInfo= null;
                try {
                    roomInfo = getValues(req);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean bs=service.updateInfo(roomInfo);
                resp.setContentType("application/json;chartset=utf-8");
                String jsons2=JSON.toJSONString(R.fail("修改失败了..."));
                if(bs){
                    jsons2=JSON.toJSONString(R.ok());
                }
                resp.getWriter().print(jsons2);
                break;
            default:
                query(req,resp);
                break;
        }

    }




    /**
     * 根据前端传值获取对应的值信息，并把值信息封装到RoomInfo 对象中
     * @param req
     * @return
     */
    public ReserveInfo getValues(HttpServletRequest req) throws ParseException {
        //根据id判断是修改还新增，如修改id不为空
        ReserveInfo info=new ReserveInfo();
        String id=req.getParameter("id")==null?null:req.getParameter("id");
        if(id!=null){
            info.setId(Integer.parseInt(id));
        }
        String room_id=req.getParameter("roomId");
        String id_card=req.getParameter("idCard");
        String tel=req.getParameter("tel");
        String counts=req.getParameter("counts");
        String start_time=req.getParameter("startTime");
        String end_time=req.getParameter("endTime");
        String state=req.getParameter("state")==null?"0":req.getParameter("state");
        String is_pay=req.getParameter("isPay")==null?"0":req.getParameter("isPay");
        info.setRoom_id(Integer.parseInt(room_id));
        info.setId_card(id_card);
        info.setTel(tel);
        if(counts==null){
             counts="0";
        }
        info.setCounts(Integer.parseInt(counts));
        info.setStart_time(DateUtil.stringToDate(start_time));
        info.setEnd_time(DateUtil.stringToDate(end_time));
        info.setState(Integer.parseInt(state));
        info.setIs_pay(Integer.parseInt(is_pay));
        return info;
    }
    /**
     * 查询
     */
    public void query(HttpServletRequest req,
                      HttpServletResponse resp) throws ServletException, IOException {

        //接收前端的传值  当前页 每页显示记录数
        String currPage=req.getParameter("page");
        String limit=req.getParameter("limit");
        String content=req.getParameter("content")==null?null:req.getParameter("content");
        //判断
        if(currPage==null || "".equals(currPage.trim())){
            currPage="1";
        }

        if(limit==null || "".equals(limit.trim())){
            limit="10";
        }

        //通过service层接口拿到分页对象
        PageBean<ReserveInfo> pageBean=null;
        try {
            pageBean = service.getPage(currPage,limit,content);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //转到前端
        JsonObject object=new JsonObject();
        object.setCode(0);
        object.setMsg("");
        object.setCount(pageBean.getTotalCount());
        object.setData(pageBean.getPageData());

        resp.setContentType("application/json;chartset=utf-8");
        String jsons= JSON.toJSONString(object);
        resp.getWriter().print(jsons);
    }
}
