package com.books.servlets;

import com.books.entities.Book;
import com.books.services.AuthorService;
import com.books.services.abstracts.AuthorServiceable;
import com.books.utils.NavigateServletConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookCreateServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCreateServlet.class);
    private AuthorServiceable authorService = AuthorService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = new Book();
        req.setAttribute("book", book);
        req.setAttribute("canAuthorsAdd", authorService.getAllAuthors());
        getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_EDIT_JSP_PATH).forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_EDIT_SERVLET_PATH).forward(req, resp);
    }
}
