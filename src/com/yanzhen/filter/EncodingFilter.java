package com.yanzhen.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    String code=null;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if(code!=null){
            req.setCharacterEncoding(code);
            resp.setContentType("text/html;charset="+code);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        code=config.getInitParameter("codeEning");
    }

}
