package com.books.servlets;

import com.books.entities.Book;
import com.books.services.AuthorService;
import com.books.services.BookService;
import com.books.services.PublisherService;
import com.books.services.abstracts.AuthorServiceable;
import com.books.services.abstracts.BookServiceable;
import com.books.services.abstracts.PublisherServiceable;
import com.books.utils.NavigateServletConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookCreateServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookCreateServlet.class);
    private BookServiceable bookService = BookService.getInstance();
    private AuthorServiceable authorService = AuthorService.getInstance();
    private PublisherServiceable publisherService = PublisherService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book;

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                NavigateServletConstants.BOOK_EDIT_JSP_PATH);
        book = new Book();
        req.setAttribute("book", book);
        req.setAttribute("canAuthorsAdd", authorService.getAllAuthors());
        req.setAttribute("publishers", publisherService.getAllPublishers());

        bookService.addBookWithoutPublisher(book);
      //  dispatcher.forward(req, resp);
        resp.sendRedirect("/books/list");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect("/books/list");//getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_EDIT_SERVLET_PATH).forward(req, resp);
    }
}
