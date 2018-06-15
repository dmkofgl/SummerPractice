package com.books.servlets;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.services.AuthorService;
import com.books.services.BookService;
import javafx.util.Pair;
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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BookEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookEditServlet.class);
    BookService bookService = new BookService();
    AuthorService authorService = new AuthorService();
    ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = null;
        RequestDispatcher dispatcher;
        int bookId = (Integer) req.getAttribute("id");
        try {
            book = bookService.getBookById(bookId);
        } catch (IndexOutOfBoundsException indexOut) {
            logger.info("IndexOutOfBoundsException, can't found this book id" + bookId);
            dispatcher = getServletContext().getRequestDispatcher(
                    "/jsp/NotFound.jsp");
        }
        addAvailableAuthors(req, book.getAuthors());
        setAttributesAndForward("/jsp/BookEdit.jsp", req, resp,
                new Pair<String, Object>("book", book));

    }

    private void addAvailableAuthors(HttpServletRequest request, Collection<Person> exclude) {
        List<Person> canAuthorsAdd = authorService.getAllAuthors().stream()
                .filter(author -> !(exclude.contains(author))).collect(Collectors.toList());
        request.setAttribute("canAuthorsAdd", canAuthorsAdd);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String removeAuthor = req.getParameter("removeAuthor");
        String addAuthor = req.getParameter("addedAuthor");
        Integer bookId = (Integer) req.getAttribute("bookId");
        Book book = bookService.getBookById(bookId);

        if (removeAuthor != null) {
            Integer removedAuthorId = Integer.valueOf(removeAuthor);
            book.removeAuthor(removedAuthorId);
            bookService.setBook(book.getId(), book);
            addAvailableAuthors(req, book.getAuthors());
            SaveBook(req, book);
            setAttributesAndForward("/jsp/BookEdit.jsp", req, resp,
                    new Pair<String, Object>("book", book));
        }
        if (addAuthor != null) {
            Integer addedAuthorId = Integer.valueOf(addAuthor);
            book.getAuthors().add(authorService.getAuthorById(addedAuthorId));

            bookService.setBook(book.getId(), book);
            addAvailableAuthors(req, book.getAuthors());
            SaveBook(req, book);
            setAttributesAndForward("/jsp/BookEdit.jsp", req, resp,
                    new Pair<String, Object>("book", book));
        } else {
            SaveBook(req, book);
            resp.sendRedirect("/books/list");
        }
    }

    private void SaveBook(HttpServletRequest req, Book book) {

        Integer bookId = (Integer) req.getAttribute("bookId");
        book.setName(req.getParameter("name"));
        book.getPublisher().setName(req.getParameter("publisher"));
        String date = req.getParameter("publishDate");
        Date bookDate = new Date();
        try {
            bookDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            logger.info("incorrect date in book#" + bookId);
        }
        book.setPublishDate(bookDate);
        bookService.setBook(bookId, book);
    }

    private void setAttributesAndForward(String forwardTo, HttpServletRequest req, HttpServletResponse resp,
                                         Pair<String, Object>... attributes) throws ServletException, IOException {

        for (Pair<String, Object> attribute : attributes) {
            req.setAttribute(attribute.getKey(), attribute.getValue());
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                "/jsp/BookEdit.jsp");
        dispatcher.forward(req, resp);
    }
}
