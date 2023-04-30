package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.CheckOutRoom;
import com.yanzhen.model.RoomType;
import com.yanzhen.service.CheckOutService;
import com.yanzhen.service.impl.CheckOutServiceImpl;
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
import java.util.Arrays;
import java.util.List;

@WebServlet("/checkOutServlet")
public class CheckOutServlet extends HttpServlet {

    CheckOutService service=new CheckOutServiceImpl();

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
        switch (flag){
            case "insert":
                String remark=req.getParameter("remark");
                String resID=req.getParameter("resID");//预定id
                String author="admin";//后续有了登录信息后使用登录用户名称
                CheckOutRoom checkOutRoom=new CheckOutRoom();
                checkOutRoom.setReserveId(Integer.parseInt(resID));
                checkOutRoom.setRemark2(remark);
                checkOutRoom.setAuthor(author);
                boolean bs= false;
                try {
                    bs = service.saveInfo(checkOutRoom);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                resp.setContentType("application/json;chartset=utf-8");
                String jsons=JSON.toJSONString(R.fail("退房失败了..."));
                if(bs){
                    jsons=JSON.toJSONString(R.ok());
                }
                resp.getWriter().print(jsons);
                break;
            case "delete":
                String ids=req.getParameter("ids");
                //把接收到的字符串转成集合
                List<String> idList= Arrays.asList(ids.split(","));
                for(String id:idList){
                    service.deleteById(Integer.parseInt(id));
                }
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;

            case "queryId":
                String id=req.getParameter("id");
                try {
                    CheckOutRoom info= service.queryByID(Integer.parseInt(id));
                    resp.setContentType("application/json;chartset=utf-8");
                    resp.getWriter().print(JSON.toJSONString(info));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
            default:
                queryList(req,resp);
                break;
        }


    }


    /**
     * 分页查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void queryList(HttpServletRequest req,
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
        PageBean<CheckOutRoom> pageBean=null;
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
