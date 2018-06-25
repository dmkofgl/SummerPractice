package com.books.servlets;

import com.books.entities.User;
import com.books.services.AuthenticateService;
import com.books.services.EncryptService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private AuthenticateService authenticateService = AuthenticateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String login = request.getParameter("user");
        String pwd = EncryptService.md5Encrypt(request.getParameter("pwd"));

        User user = authenticateService.takeUser(login, pwd);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60);
            Cookie userName = new Cookie("user", login);
            userName.setMaxAge(30 * 60);
            response.addCookie(userName);
            response.sendRedirect("books/list");
        } else {
            response.sendRedirect("/loginPage?error=Invalid login or password. Please try again.");
        }
    }
}
