package com.books.servlets;

import com.books.entities.Book;
import com.books.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookCreateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookCreateServlet.class);
    BookService service = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Book book;
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                "/jsp/BookEdit.jsp");
        book = new Book(service.generateId());
        req.setAttribute("book", book);
        dispatcher.forward(req, resp);
    }
}
