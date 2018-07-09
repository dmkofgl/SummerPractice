package com.books.services;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.abstracts.BookServiceable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class BookServiceableTest {
    @Autowired
    private BookServiceable filter;
    private Person[] authors;
    private Book[] books;

    @Before
    public void setUp() {
        Date date = new Date();
        authors = new Person[]{
                new Person(0, "Ivan", "First"),
                new Person(1, "Andrew", "Second"),
                new Person(2, "Sam", "Third"),
                new Person(3, "Andrew", "Fourth"),
                new Person(4, "Дмитрий", "Fourth")
        };
        books = new Book[]{
                new Book(0, "first book", date, new Publisher(0, "Publisher1"),
                        authors[0], authors[1]),
                new Book(1, "Second book", date, new Publisher(1, "Publisher2"),
                        authors[1]),
                new Book(2, "Third book", date, new Publisher(2, "Publisher3"),
                        authors[2]),
                new Book(3, "Third book", date, new Publisher(3, "Publisher4"),
                        authors[4])
        };
    }

    @Test
    public void testFilterByName() {
        List<Book> testList = new ArrayList<Book>();
        testList = new ArrayList<>(Arrays.asList(books));
        Assert.assertEquals(testList.toString(), filter.filterByAuthorName(testList, "").toString());
        testList.clear();
        testList.add(books[2]);
        Assert.assertEquals(testList.toString(), filter.filterByAuthorName(testList, "am").toString());
        Assert.assertEquals(testList.toString(), filter.filterByAuthorName(testList, "Am").toString());

        Assert.assertEquals(0, filter.filterByAuthorName(testList, "asfgfgd").size());

        testList.clear();
        testList.add(books[3]);
        Assert.assertEquals(testList.toString(), filter.filterByAuthorName(testList, "дмит").toString());
    }

}
