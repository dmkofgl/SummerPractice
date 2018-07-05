package com.books;

import com.books.dao.abstracts.AuthorDAO;
import com.books.dao.abstracts.BookDAO;
import com.books.dao.abstracts.PublisherDAO;
import com.books.dao.concrete.SQL.AuthorSQLDAO;
import com.books.entities.Book;
import com.books.entities.Publisher;
import com.books.services.BookService;
import com.books.services.abstracts.BookServiceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class BookApplication {

    private static final Logger logger = LoggerFactory.getLogger(BookApplication.class);

    static List<Book> books;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ClassLoader.getSystemResource("src/main/webapp/WEB-INF/applicationContext.xml").toString());

        BookServiceable filter = context.getBean(BookService.class);

        PublisherDAO publisherDAO =context.getBean(PublisherDAO.class);
        AuthorDAO authorDAO =context.getBean(AuthorSQLDAO.class);
        System.out.println(publisherDAO.getPublisherById(1));
      Publisher p=  publisherDAO.getPublisherById(1);
      p.setName("asd");
      publisherDAO.saveItem(p.getId(),p);
       // publisherDAO.remove(p);
        System.out.println(publisherDAO.getPublisherById(1));
        System.out.println(authorDAO.getList());
        System.out.println(publisherDAO.getList());
        BookDAO bookDAO =context.getBean(BookDAO.class);
       Book b = bookDAO.getBookById(1);



    }


}

