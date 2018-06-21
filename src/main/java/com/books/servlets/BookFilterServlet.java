package com.books.servlets;

import com.books.services.BookService;
import com.books.utils.NavigateServletConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookFilterServlet extends HttpServlet {
    BookService service = BookService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("list", service.getAllBooks());

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_FILTER_JSP_PATH);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        req.setAttribute("list", service.filterByAuthorName(query));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_FILTER_JSP_PATH);
        dispatcher.forward(req, resp);
    }
}
