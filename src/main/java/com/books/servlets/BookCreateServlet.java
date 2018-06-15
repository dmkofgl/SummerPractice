package com.books.servlets;

import com.books.entities.Book;
import com.books.services.AuthorService;
import com.books.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class BookCreateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookCreateServlet.class);
    BookService service =new BookService();
    AuthorService authorService = new AuthorService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book;
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                "/jsp/BookEdit.jsp");
        book = new Book(service.generateId());
        req.setAttribute("book", book);
        req.setAttribute("canAuthorsAdd", authorService.getAllAuthors());
        service.addBook(book);
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = (Book) req.getAttribute("book");
        service.addBook(book);
        resp.sendRedirect("/books/list");

    }
}
