package com.clx.filter;

import com.alibaba.fastjson.JSON;
import com.clx.common.BaseContext;
import com.clx.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    // Path matcher, which supports wildcards
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1 Obtain the URI of the request
        String requestURI = request.getRequestURI();

        log.info("The request was intercepted：{}", requestURI);

        //Define the path of requests that don't need to be processed
        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "user/login"
        };

        //2 Determine whether the request needs to be processed
        boolean check = check(urls, requestURI);

        //3 If it does not need to be processed, it is released directly
        if (check){
            log.info("This request {} does not need to be processed",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4-1 Determine the login status and release the login if you have logged in
        if (request.getSession().getAttribute("employee") != null){
            log.info("The user is logged in and the user ID is: {}", request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request,response);
            return;
        }

        //4-2 Determine the login status and release the login if you have already logged in
        if (request.getSession().getAttribute("user") != null){
            log.info("The user is logged in and the user ID is: {}", request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("The user is not logged in");
        //5. If you are not logged in, the result of not logging in will be returned,
        // and the corresponding data will be sent to the client page in the form of output stream
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


    }


    public boolean check(String[] urls, String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url,requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }


}
