package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.CommentInfo;
import com.yanzhen.model.UserInfo;
import com.yanzhen.service.CommentInfoService;
import com.yanzhen.service.impl.CommentInfoServiceImpl;
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

@WebServlet("/commentServlet")
public class CommentServlet extends HttpServlet {

    CommentInfoService service=new CommentInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
        switch (flag){
            case "delete":
                String ids=req.getParameter("ids");
                //把接收到的字符串转成集合
                List<String> idList= Arrays.asList(ids.split(","));
                for(String id:idList){
                    service.deleteById(Integer.parseInt(ids));
                }
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
            default:
                query(req,resp);
                break;
        }

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
        PageBean<CommentInfo> pageBean=null;
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
