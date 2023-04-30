package com.yanzhen.filter;

import com.yanzhen.model.UserInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
        HttpSession session=request.getSession();
        UserInfo user= (UserInfo) session.getAttribute("user");
//        if(user==null){
//            response.sendRedirect("admin/login.jsp");
//        }

        //不需要拦截的url
        String [] urls={"login.jsp",".js",".css","ico",".jpg",".png"};
        //获取请求url
        String spath=request.getServletPath();
        boolean flag=true;
        for(String str:urls){
            if(spath.endsWith(str)){
                flag=false;
                break;
            }
        }
        if(spath.contains("loginServlet")){
            flag=false;
        }
        if(flag){
            if(user!=null){
                chain.doFilter(req, resp);
            }else{
                response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
            }
        }else{
            chain.doFilter(req, resp);
        }

//        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
