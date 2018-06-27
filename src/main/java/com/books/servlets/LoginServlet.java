package com.books.servlets;

import com.books.entities.User;
import com.books.services.AuthenticateService;
import com.books.services.EncryptService;
import com.books.services.abstracts.AuthenticateServiceable;
import com.books.services.abstracts.EncryptServiceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    private AuthenticateServiceable authenticateService = AuthenticateService.getInstance();
    private EncryptServiceable encryptService = EncryptService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("init login servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String login = request.getParameter("user");
        String pwd = encryptService.md5Encrypt(request.getParameter("pwd"));
        if (authenticateService.checkExistsUser(login)) {
            User user = authenticateService.takeUser(login, pwd);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30 * 60);
                response.sendRedirect("books/list");
            } else {
                request.getSession(true).setAttribute("message", "Invalid password. Please try again.");
                response.sendRedirect("/loginPage");
            }
        } else {
            request.getSession(true).setAttribute("message", "Invalid login. Please try again.");
            response.sendRedirect("/loginPage");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("destroy login servlet");
    }
}
