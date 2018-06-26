package com.books.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("init logout servlet");
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("destroy logout servlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        session.setAttribute("message","Sign in please");
        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("/loginPage?logout=true");
    }

}

