package com.books.servlets;


import com.books.entities.Book;
import com.books.services.BookService;
import com.books.storage.abstracts.Repository;
import com.books.storage.concrete.BookRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookEditRouterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                "/jsp/BookEdit.jsp");
        BookService service = new BookService();
        Book book;
        int bookId = Integer.parseInt(req.getPathInfo().substring(1));
        try {
            book = service.getBookById(bookId);
        } catch (IndexOutOfBoundsException indexOut) {
            book = null;
        }
        if (book != null) {
            req.setAttribute("action", "edit");
            req.setAttribute("book", book);

        } else {
            req.setAttribute("action", "create");
            req.setAttribute("book", new Book());

        }
        dispatcher.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {

        BookService service = new BookService();
        int bookId = Integer.valueOf(req.getParameter("id"));
        Book book = service.getBookById(bookId);
        if (book == null) {
            book = new Book();
        }

        Book editedBook = new Book(bookId, req.getParameter("name"),
                                   book.getPublishDate(),
                                   book.getPublisher(),
                                   book.getAuthors());

        service.setBook(bookId, editedBook);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                "/");
        dispatcher.forward(req, resp);
    }
}
