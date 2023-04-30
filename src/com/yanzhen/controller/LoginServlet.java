package com.yanzhen.controller;

import com.alibaba.fastjson.JSON;
import com.yanzhen.model.UserInfo;
import com.yanzhen.service.UserInfoService;
import com.yanzhen.service.impl.UserInfoServiceImpl;
import com.yanzhen.util.R;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    UserInfoService service=new UserInfoServiceImpl();

    @Override
    protected void service(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        String flag=req.getParameter("flag")==null?null:req.getParameter("flag");
        switch (flag){
            case "loginIn":
                resp.setContentType("application/json;chartset=utf-8");
                //接收用户名 密码
                String username=req.getParameter("username");
                String pwd= req.getParameter("password");
                try {
                    UserInfo userInfo= service.queryUserNameAndPwd(username,pwd);
                    if(userInfo!=null){
                        session.setAttribute("user",userInfo);
                        resp.getWriter().print(JSON.toJSONString(R.ok()));
                    }else{
                        resp.getWriter().print(JSON.toJSONString(R.fail("登录失败，重新登录")));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;

            default:
                session.invalidate();
                resp.sendRedirect("admin/login.jsp");
                break;

        }

    }
}
