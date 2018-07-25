package com.books.config;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.exceptions.UncorrectedQueryException;
import com.books.services.abstracts.AuthorService;
import com.books.services.abstracts.BookService;
import com.books.services.abstracts.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class BookRestController {
    @Autowired
    private BookService bookService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private AuthorService authorService;


    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books;
        try {
            books = bookService.getAllBooks();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
        Book book = null;
        try {
            book = bookService.getBookById(id);
        } catch (UncorrectedQueryException e) {
            return new ResponseEntity<>(book, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public ResponseEntity setBook(@RequestBody Book book) {
        HttpStatus status = HttpStatus.OK;
        try {
            bookService.saveBook(book);
        } catch (Exception e) {
            status =HttpStatus.FORBIDDEN;
        }
        //TODO if not ok
        return new ResponseEntity(status);
    }


    @RequestMapping(value = "/books/new", method = RequestMethod.POST)
    public ResponseEntity addBook(@RequestBody Book book) {
        bookService.addBook(book);
        //TODO if not ok
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public HttpStatus deletetBook(@PathVariable("id") Integer bookId) {
        try {
            bookService.removeBook(bookId);
        } catch (UncorrectedQueryException e) {
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Publisher> getPublisher(@PathVariable("id") int id) {
        Publisher publisher = publisherService.getPublisherById(id).get();
        //TODO if not ok
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }


    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publisher = publisherService.getAllPublishers();
        //TODO if not ok
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }


    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.PUT)
    public HttpStatus setPublisher(Publisher publisher) {
        publisherService.savePublisher(publisher);
        //TODO if not ok
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/publishers/new", method = RequestMethod.POST)
    public HttpStatus addPublisher(Publisher publisher) {
        publisherService.addPublisher(publisher);
        //TODO if not ok
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.DELETE)
    public HttpStatus removePublisher(@PathVariable("id") int id) {
        publisherService.removePublisher(id);
        //TODO if not ok
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/authors/new", method = RequestMethod.POST)
    public HttpStatus createAuthor(Person author) {
        authorService.addAuthor(author);
        //TODO if not ok
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getAuthor(@PathVariable("id") int id) {
        Person author = authorService.getAuthorById(id);
        //TODO if not ok
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllAuthors() {
        List<Person> author = authorService.getAllAuthors();
        //TODO if not ok
        return new ResponseEntity<>(author, HttpStatus.OK);
    }


    @RequestMapping(value = "/authors/{id}", method = RequestMethod.PUT)
    public HttpStatus setAuthor(Person author) {
        authorService.saveAuthor(author);
        //TODO if not ok
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/authors/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteAuthor(@PathVariable("id") int id) {
        authorService.removeAuthor(id);
        //TODO if not ok
        return HttpStatus.OK;
    }


}
