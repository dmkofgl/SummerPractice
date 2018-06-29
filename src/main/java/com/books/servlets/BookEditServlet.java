package com.books.servlets;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.AuthorService;
import com.books.services.BookService;
import com.books.services.PublisherService;
import com.books.services.abstracts.AuthorServiceable;
import com.books.services.abstracts.BookServiceable;
import com.books.services.abstracts.PublisherServiceable;
import com.books.utils.NavigateServletConstants;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BookEditServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookEditServlet.class);
    @Autowired
    BookServiceable bookService;
    @Autowired
    AuthorServiceable authorService;
    @Autowired
    PublisherServiceable publisherService;

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("init book edit servlet");

    //    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("destroy book edit servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = null;
        RequestDispatcher dispatcher;
        String action = req.getPathInfo().substring(1);
        String path = NavigateServletConstants.BOOK_EDIT_JSP_PATH;
        Integer bookId = -1;
        if (!(action == null && action.isEmpty())) {
            try {
                bookId = Integer.parseInt(action);
            } catch (NumberFormatException numberFormatException) {
                path = NavigateServletConstants.NOT_FOND_JSP_PATH;
                resp.sendRedirect(path);
                return;
            }
        }
        try {
            book = bookService.getBookById(bookId);
        } catch (IndexOutOfBoundsException indexOut) {
            logger.info("IndexOutOfBoundsException, can't found this book id" + bookId);
        }
        addAvailableAuthors(req, book.getAuthors());
        setAttributesAndForward(path, req, resp,
                new Pair<>("book", book),
                new Pair<>("publishers", publisherService.getAllPublishers()));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String removeAuthor = req.getParameter("removeAuthor");
        String addAuthor = req.getParameter("addedAuthor");
        String changePublisher = req.getParameter("changePublisher");

        Book book = SaveBook(req);

        if (removeAuthor != null) {
            Integer removedAuthorId = Integer.valueOf(removeAuthor);
            book.removeAuthor(removedAuthorId);
        }
        if (addAuthor != null) {
            Integer addedAuthorId = Integer.valueOf(addAuthor);
            book.addAuthor(authorService.getAuthorById(addedAuthorId));
        }
        if (changePublisher != null) {
            Integer changePublisherId = Integer.valueOf(changePublisher);
            book.setPublisher(publisherService.getPublisherById(changePublisherId));
        }
        if (req.getParameter("confirmChange") != null) {
            resp.sendRedirect(NavigateServletConstants.BOOK_LIST_SERVLET_PATH);
            bookService.saveBook(book);
            return;
        }

        addAvailableAuthors(req, book.getAuthors());

        setAttributesAndForward(NavigateServletConstants.BOOK_EDIT_JSP_PATH, req, resp,
                new Pair<>("book", book),
                new Pair<>("publishers", publisherService.getAllPublishers()));
    }

    private Book SaveBook(HttpServletRequest req) {

        Book book = new Book();

        book.setName(req.getParameter("name"));

        if (!req.getParameter("bookId").isEmpty()) {
            Integer bookId = Integer.valueOf(req.getParameter("bookId"));
            book.setId(bookId);
        }
        if (!req.getParameter("publisherId").isEmpty()) {
            Integer publisherId = Integer.valueOf(req.getParameter("publisherId"));
            Publisher publisher = publisherService.getPublisherById(publisherId);
            book.setPublisher(publisher);
        }
        if (req.getParameterValues("authorsId") != null) {
            List<Person> authors = Arrays.stream(req.getParameterValues("authorsId"))
                    .map(id -> authorService.getAuthorById(Integer.parseInt(id)))
                    .collect(Collectors.toList());
            book.setAuthors(authors);
        }
        String date = req.getParameter("publishDate");
        Date bookDate = new Date();
        try {
            bookDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            logger.info("incorrect date in book#" + book.getId());
        }
        book.setPublishDate(bookDate);
        return book;

    }

    private void setAttributesAndForward(String forwardTo, HttpServletRequest req, HttpServletResponse resp,
                                         Pair<String, Object>... attributes) throws ServletException, IOException {
        for (Pair<String, Object> attribute : attributes) {
            req.setAttribute(attribute.getKey(), attribute.getValue());
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
                forwardTo);
        dispatcher.forward(req, resp);
    }

    private void addAvailableAuthors(HttpServletRequest request, Collection<Person> exclude) {
        List<Person> canAuthorsAdd = authorService.getAllAuthors().stream()
                .filter(author -> !(exclude.contains(author))).collect(Collectors.toList());
        request.setAttribute("canAuthorsAdd", canAuthorsAdd);
    }
}
