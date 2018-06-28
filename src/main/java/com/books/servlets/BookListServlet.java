
package com.books.servlets;


import com.books.services.BookService;
import com.books.utils.NavigateServletConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class BookListServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookListServlet.class);
    private BookService service = BookService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("init book list servlet");
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("destroy book list servlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", service.getAllBooks());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_LIST_JSP_PATH);
        dispatcher.forward(req, resp);


    }
}


