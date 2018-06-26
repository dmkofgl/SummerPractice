package com.books.servlets.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        this.context.log("Requested Resource::" + uri);
        //TODO why?
        if(uri.endsWith("css") )
        {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(true);
        Object user = null;

        user = session.getAttribute("user");


        if (user == null && !(uri.endsWith("loginPage") || uri.endsWith("LoginServlet"))) {
            session.setAttribute("message","Sign in please");
            this.context.log("Unauthorized access request");
            res.sendRedirect("/loginPage");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //close any resources here
        this.context.log("AuthenticationFilter destroyed");
    }

}
