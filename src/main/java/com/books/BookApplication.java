package com.books;

import com.books.config.CustomContextListener;
import com.books.dao.abstracts.AuthorDAO;
import com.books.dao.abstracts.PublisherDAO;
import com.books.dao.concrete.SQL.AuthorSQLDAO;
import com.books.dao.concrete.SQL.PublisherSQLDAO;
import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.BookService;
import com.books.services.abstracts.BookServiceable;
import com.books.utils.DatabaseCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookApplication {

    private static final Logger logger = LoggerFactory.getLogger(BookApplication.class);

    static List<Book> books;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(ClassLoader.getSystemResource("applicationContext.xml").toString());

        BookServiceable filter = context.getBean(BookService.class);
      /*  System.out.println(filter.getAllBooks());
        Date date = new Date();
        List<Person> authors = new ArrayList<>();

        authors.add(new Person(0, "Ivan", "First"));
        authors.add(new Person(1, "Andrew", "Second"));
        authors.add(new Person(2, "Sam", "Third"));
        authors.add(new Person(3, "Andrew", "Fourth"));

        books = new ArrayList<Book>();
        books.add(new Book(0, "first book", date, new Publisher(0, "Publisher1"),
                authors.get(0), authors.get(1)));
        books.add(new Book(1, "Second book", date, new Publisher(1, "Publisher2"),
                authors.get(1)));
        books.add(new Book(2, "Third book", date, new Publisher(2, "Publisher3"),
                authors.get(2)));
        books.add(new Book(3, "Third book", date, new Publisher(3, "Publisher4"),
                authors.get(3)));
/*
        logger.info("View books");
        logger.debug("{}", books);
        logger.info("View filtered by author name books that contains 'an'");
        logger.debug("{}", books);
        logger.debug("{}", filter.filterByAuthorName(books, "an"));
        System.out.println(ClassLoader.getSystemResource("springXML/daos.xml"));
        System.out.println(ClassLoader.getSystemResource("applicationContext.xml").toString());*/

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
    }


}

