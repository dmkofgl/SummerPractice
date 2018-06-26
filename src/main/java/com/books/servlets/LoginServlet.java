package com.books.servlets;

import com.books.entities.User;
import com.books.services.AuthenticateService;
import com.books.services.EncryptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private AuthenticateService authenticateService = AuthenticateService.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("init login servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String login = request.getParameter("user");
        String pwd = EncryptService.md5Encrypt(request.getParameter("pwd"));

        User user = authenticateService.takeUser(login, pwd);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);
            response.sendRedirect("books/list");
        } else {
            request.getSession(true).setAttribute("message","Invalid login or password. Please try again.");
            response.sendRedirect("/loginPage");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("destroy login servlet");
    }
}
