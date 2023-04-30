package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.RoomType;
import com.yanzhen.service.TypeInfoService;
import com.yanzhen.service.impl.TypeInfoServiceImpl;
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
import java.util.Date;
import java.util.List;

@WebServlet("/typeInfoServlet")
public class TypeInfoServlet extends HttpServlet {
    TypeInfoService service=new TypeInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
      //接收前端传来的标识，根据标识进行执行代码块
       String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
       switch (flag){
           case "queryTypeList":
               try {
                   PageBean<RoomType> rlist=service.getPage("1","20",null);
                   resp.setContentType("application/json;chartset=utf-8");
                   String jsons=JSON.toJSONString(rlist.getPageData());
                   resp.getWriter().print(jsons);
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
               break;
           case "insert":
               //调用添加方法
               RoomType type= getValues(req);
               boolean bs=service.saveInfo(type);
               resp.setContentType("application/json;chartset=utf-8");
               String jsons=JSON.toJSONString(R.fail("添加失败了..."));
               if(bs){
                    jsons=JSON.toJSONString(R.ok());
               }
               resp.getWriter().print(jsons);
               break;
           case "update":
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
           case "queryByID":
               String id=req.getParameter("id");
               try {
                   RoomType typeInfo= service.queryByID(Integer.parseInt(id));
                   resp.setContentType("application/json;chartset=utf-8");
                   resp.getWriter().print(JSON.toJSONString(typeInfo));
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
               break;
           case "updateSubmit":
               //调用添加方法
               RoomType type2= getValues(req);
               boolean bs2=service.updateInfo(type2);
               resp.setContentType("application/json;chartset=utf-8");
               String jsons2=JSON.toJSONString(R.fail("修改失败了..."));
               if(bs2){
                   jsons2=JSON.toJSONString(R.ok());
               }
               resp.getWriter().print(jsons2);
               break;
           default:
               queryList(req,resp);
               break;
       }

    }

    public RoomType getValues(HttpServletRequest req){
        //接收前端传过来的值信息
        String id=req.getParameter("id")==null?null:req.getParameter("id");
        String name=req.getParameter("typeName");
        String price=req.getParameter("price");
        String remark=req.getParameter("remark");
        RoomType type= new RoomType();
        type.setTypeName(name);
        type.setPrice(Double.parseDouble(price));
        type.setRemark(remark);
        type.setCreateAuthor("admin");//后续改成登录用户
        type.setCreateTime(new Date());
        if(id!=null){//不为空修改
            type.setId(Integer.parseInt(id));
        }
        return type;
    }

    /**
     * 查询方法
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
        PageBean<RoomType> pageBean=null;
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
        String jsons=JSON.toJSONString(object);
        resp.getWriter().print(jsons);
    }
}
