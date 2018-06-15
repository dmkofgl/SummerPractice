package com.books.servlets;


import com.books.BookApplication;
import com.books.entities.Book;
import com.books.services.BookService;
import com.books.storage.abstracts.Repository;
import com.books.storage.concrete.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookEditRouterServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BookEditRouterServlet.class);
    BookService service = new BookService();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher;

        String action = req.getPathInfo().substring(1);
        if (isNumber(action)) {
            getServletConfig().getServletContext();
            dispatcher = getServletContext().getRequestDispatcher(
                    "/books/edit");
            int bookId = Integer.parseInt(action);
            req.setAttribute("id", bookId);
            dispatcher.forward(req, resp);

        } else if (action.equals("new")) {
            dispatcher = getServletContext().getRequestDispatcher(
                    "/books/create");
            dispatcher.forward(req, resp);
        } else {
            dispatcher = getServletContext().getRequestDispatcher(
                    "/jsp/NotFound.jsp");
            dispatcher.forward(req, resp);
        }


    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        int bookId = Integer.valueOf(req.getParameter("bookId"));

        Book book = service.getBookById(bookId);
        if (book == null) {
            dispatcher = getServletContext().getRequestDispatcher(
                    "/books/create");
            dispatcher.forward(req, resp);
        } else {
            req.setAttribute("bookId", bookId);
            dispatcher = getServletContext().getRequestDispatcher(
                    "/books/edit");
            dispatcher.forward(req, resp);
        }

    }

    public boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}
