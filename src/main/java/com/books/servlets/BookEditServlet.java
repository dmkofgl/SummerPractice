package com.books.servlets;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.services.AuthorService;
import com.books.services.BookService;
import com.books.utils.Constants;
import com.books.utils.NavigateServletConstants;
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = null;
        RequestDispatcher dispatcher;
        String action = req.getPathInfo().substring(1);
        String path = "/jsp/BookEdit.jsp";
        Integer bookId = -1;
        if (!(action == null && action.isEmpty())) {
            try {
                bookId = Integer.parseInt(action);
            } catch (NumberFormatException numberFormatException) {
                path = NavigateServletConstants.NOT_FOND_JSP_PATH;
            }
        }
        try {
            book = bookService.getBookById(bookId);
        } catch (IndexOutOfBoundsException indexOut) {
            logger.info("IndexOutOfBoundsException, can't found this book id" + bookId);
            path = NavigateServletConstants.NOT_FOND_JSP_PATH;
        }
        addAvailableAuthors(req, book.getAuthors());
        setAttributesAndForward(path, req, resp,
                new Pair<String, Object>("book", book));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String removeAuthor = req.getParameter("removeAuthor");
        String addAuthor = req.getParameter("addedAuthor");
        int bookId = Integer.valueOf(req.getParameter("bookId"));

        Book book = bookService.getBookById(bookId);
        SaveBook(req, book);
        if (removeAuthor != null) {
            Integer removedAuthorId = Integer.valueOf(removeAuthor);
            bookService.removeAuthorBook(bookId, removedAuthorId);
        }
        if (addAuthor != null) {
            Integer addedAuthorId = Integer.valueOf(addAuthor);
            bookService.addAuthorBook(bookId, addedAuthorId);

        }
        if (req.getParameter("confirmChange") != null) {
            SaveBook(req, book);
            resp.sendRedirect(NavigateServletConstants.BOOK_LIST_SERVLET_PATH);
            bookService.setBook(bookId, book);
            return;
        }
        addAvailableAuthors(req, book.getAuthors());
        bookService.setBook(bookId, book);
        setAttributesAndForward(NavigateServletConstants.BOOK_EDIT_JSP_PATH, req, resp,
                new Pair<String, Object>("book", book));
    }

    private void SaveBook(HttpServletRequest req, Book book) {

        Integer bookId = Integer.valueOf(req.getParameter("bookId"));
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

    }

    private void setAttributesAndForward(String forwardTo, HttpServletRequest req, HttpServletResponse resp,
                                         Pair<String, Object>... attributes) throws ServletException, IOException {

        for (Pair<String, Object> attribute : attributes) {
            req.setAttribute(attribute.getKey(), attribute.getValue());
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                NavigateServletConstants.BOOK_EDIT_JSP_PATH);
        dispatcher.forward(req, resp);
    }

    private void addAvailableAuthors(HttpServletRequest request, Collection<Person> exclude) {
        List<Person> canAuthorsAdd = authorService.getAllAuthors().stream()
                .filter(author -> !(exclude.contains(author))).collect(Collectors.toList());
        request.setAttribute("canAuthorsAdd", canAuthorsAdd);
    }
}