package com.books.servlets;

import com.books.entities.Book;
import com.books.services.AuthorService;
import com.books.services.BookService;
import com.books.services.PublisherService;
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
    private static final Logger logger = LoggerFactory.getLogger(BookCreateServlet.class);
    BookService service = BookService.getInstance();
    AuthorService authorService = AuthorService.getInstance();
    PublisherService publisherService = PublisherService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book;

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                NavigateServletConstants.BOOK_EDIT_JSP_PATH);
        book = new Book(service.generateId());
        req.setAttribute("book", book);
        req.setAttribute("canAuthorsAdd", authorService.getAllAuthors());
        req.setAttribute("publishers", publisherService.getAllPublishers());

        service.addBook(book);
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_EDIT_SERVLET_PATH).forward(req, resp);


    }
}
