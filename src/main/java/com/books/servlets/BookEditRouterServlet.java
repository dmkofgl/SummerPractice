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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/BookEdit.jsp");
        BookService service = new BookService();
        Book book;
        try {
            book = service.getAllBooks().get(Integer.valueOf(req.getParameter("id")));
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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // BookService service = new BookService();
        Repository<Book> repository = new BookRepository();
        int bookId = Integer.valueOf(req.getParameter("id"));
        Book book;
        if (req.getAttribute("book") == null) {
            resp.getWriter().println(String.format("null"));
            book = repository.getCollection().get(bookId);
        } else {
            book = (Book) req.getAttribute("book");
        }
        Book editedBook = new Book(bookId, req.getParameter("name"),
                book.getPublishDate(), book.getPublisher(), book.getAuthors());
        try {
            book = repository.getCollection().get(bookId);
        } catch (IndexOutOfBoundsException indexOut) {
            resp.getWriter().println("Add book");
            repository.add(editedBook);
        }
        if (book.equals(editedBook)) {
            resp.getWriter().println("nothing change");
        } else {
            repository.setItem(bookId, editedBook);
            resp.getWriter().println("saved change");
        }
        resp.getWriter().println(String.format("Equals:%s", book.equals(editedBook)));
        resp.getWriter().println(String.format("book1:%s", book));
        resp.getWriter().println(String.format("book2:%s", editedBook));
        resp.getWriter().println(repository.getCollection());


        // RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Hello");
        // dispatcher.forward(req, resp);
    }
}
