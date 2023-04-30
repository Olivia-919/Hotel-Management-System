package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.dao.UserInfoDao;
import com.yanzhen.model.RoomInfo;
import com.yanzhen.model.UserInfo;
import com.yanzhen.service.RoomInfoService;
import com.yanzhen.service.UserInfoService;
import com.yanzhen.service.impl.RoomInfoServiceImpl;
import com.yanzhen.service.impl.UserInfoServiceImpl;
import com.yanzhen.util.JsonObject;
import com.yanzhen.util.PageBean;
import com.yanzhen.util.R;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    UserInfoService service=new UserInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        //接收前端传过来的功能标识
        String flag=req.getParameter("flag")==null?"":req.getParameter("flag");
        switch (flag){
            case "insert":
                UserInfo info=getValues(req);
                service.saveInfo(info);
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
            case "updatePassword":
                String id2=req.getParameter("id");
                String password=req.getParameter("password");
                service.updatePasswordByUserId(password,Integer.parseInt(id2));
                resp.setContentType("application/json;chartset=utf-8");
                resp.getWriter().print(JSON.toJSONString(R.ok()));
                break;
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
            case "queryId":
                String id=req.getParameter("id");
                UserInfo info1= null;
                try {
                    info1 = service.queryByID(Integer.parseInt(id));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                req.setAttribute("info",info1);
                resp.setContentType("text/html;chartset=utf-8");
                req.getRequestDispatcher("admin/user/update.jsp").forward(req,resp);
                break;
            case "update":
                UserInfo roomInfo=getValues(req);
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
    public UserInfo getValues(HttpServletRequest req){
        //根据id判断是修改还新增，如修改id不为空
        UserInfo info=new UserInfo();
        String id=req.getParameter("id")==null?null:req.getParameter("id");
        if(id!=null){
            info.setId(Integer.parseInt(id));
        }
        String nickname=req.getParameter("nickname");
        String username=req.getParameter("username");
        String identity=req.getParameter("identity");
        String tel=req.getParameter("tel");
        String email=req.getParameter("email");
        String sex=req.getParameter("sex");
        String type=req.getParameter("type");
        String password=req.getParameter("password");
        info.setNickname(nickname);
        info.setUsername(username);
        info.setTel(tel);
        info.setIdentity(identity);
        info.setEmail(email);
        info.setSex(sex);
//        info.setType(Integer.parseInt(type));
        info.setType(1);
        info.setPassword(password);
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
        PageBean<UserInfo> pageBean=null;
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
