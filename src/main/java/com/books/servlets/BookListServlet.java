
package com.books.servlets;


import com.books.services.abstracts.BookServiceable;
import com.books.utils.NavigateServletConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BookListServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookListServlet.class);
    @Autowired
    @Qualifier("BookServices")
    private BookServiceable bookService;

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("init book list servlet");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", bookService.getAllBooks());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(NavigateServletConstants.BOOK_LIST_JSP_PATH);
        dispatcher.forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("destroy book list servlet");
    }
}


