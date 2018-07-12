package com.books.config;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.abstracts.AuthorServiceable;
import com.books.services.abstracts.BookServiceable;
import com.books.services.abstracts.PublisherServiceable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
@Controller
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookServiceable bookService;
    @Autowired
    private AuthorServiceable authorService;
    @Autowired
    private PublisherServiceable publisherService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String viewBookList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "list";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {
        String query = "";
        model.addAttribute("query", query);
        model.addAttribute("books", bookService.getAllBooks());
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String filterByAuthor(@RequestParam("query") String query, Model model) {
        model.addAttribute("books", bookService.filterByAuthorName(query));
        model.addAttribute("query", query);
        return "search";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewBook(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        putDataInModel(book, model);
        return "edit";
    }


    @RequestMapping(value = {"/{bookId}"}, method = RequestMethod.POST, params = "removeAuthor")
    public String removeAuthor(@RequestParam("removeAuthor") Integer authorId, @ModelAttribute("book") Book book, Model model) {
        book.removeAuthor(authorId);
        putDataInModel(book, model);
        return "edit";
    }

    @RequestMapping(value = {"/{bookId}"}, method = RequestMethod.POST, params = "addedAuthor")
    public String addAuthor(@RequestParam("addAuthorId") Integer authorId, @ModelAttribute("book") Book book, Model model) {
        Person author = authorService.getAuthorById(authorId);
        book.addAuthor(author);
        putDataInModel(book, model);
        return "edit";
    }

    @RequestMapping(value = {"/{bookId}"}, method = RequestMethod.POST, params = "changePublisher")
    public String changePublisher(@RequestParam("changePublisherId") Integer publisherId, @ModelAttribute("book") Book book, Model model) {
        Publisher publisher = publisherService.getPublisherById(publisherId);
        book.setPublisher(publisher);
        putDataInModel(book, model);
        return "edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:list";
    }

    @RequestMapping(value = "/new")
    public String newBook(Model model) {
        Book book = new Book();
        putDataInModel(book, model);
        return "new";
    }

    @RequestMapping(value = {"/new"}, method = RequestMethod.POST, params = "addedAuthor")
    public String addAuthorToNew(@RequestParam("addAuthorId") Integer authorId, @ModelAttribute("book") Book book, Model model) {
        Person author = authorService.getAuthorById(authorId);
        book.addAuthor(author);
        putDataInModel(book, model);
        return "new";
    }

    @RequestMapping(value = {"/new"}, method = RequestMethod.POST, params = "changePublisher")
    public String changePublisherToNew(@RequestParam("changePublisherId") Integer publisherId, @ModelAttribute("book") Book book, Model model) {
        Publisher publisher = publisherService.getPublisherById(publisherId);
        book.setPublisher(publisher);
        putDataInModel(book, model);
        return "new";
    }

    private void putDataInModel(Book book, Model model) {
        List<Person> authors = authorService.getReducedAuthors(book.getAuthors());
        model.addAttribute("publishers", publisherService.getAllPublishers());
        model.addAttribute("canAuthorsAdd", authors);
        model.addAttribute("book", book);
    }
}
