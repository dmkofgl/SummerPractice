
package com.books.servlets;


import com.books.services.BookService;
import com.books.storage.concrete.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class BookList extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BookList.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookService service = new BookService();
       // BookRepository repository = new BookRepository();
        req.setAttribute("list", service.getAllBooks());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/BookList.jsp");
        dispatcher.forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("post");
        writer.flush();
    }
}


