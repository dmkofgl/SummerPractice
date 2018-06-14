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

public class BookEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookEditServlet.class);
    BookService service = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = null;
        service = new BookService();
        RequestDispatcher dispatcher;
        int bookId = (Integer) req.getAttribute("id");

        try {
            book = service.getBookById(bookId);
        } catch (IndexOutOfBoundsException indexOut) {
            logger.info("IndexOutOfBoundsException, can't found this book id");
             dispatcher = getServletContext().getRequestDispatcher(
                    "/jsp/NotFound.jsp");
        }
        req.setAttribute("book", book);
        dispatcher = getServletContext().getRequestDispatcher(
                "/jsp/BookEdit.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println();
    }
}
