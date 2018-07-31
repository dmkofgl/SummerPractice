package com.books.config;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.abstracts.AuthorService;
import com.books.services.abstracts.BookService;
import com.books.services.abstracts.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
        Book book = null;
        HttpStatus status = HttpStatus.OK;
        book = bookService.getBookById(id);
        return new ResponseEntity<>(book, status);
    }


    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public ResponseEntity setBook(@RequestBody Book book) {
        HttpStatus status = HttpStatus.OK;
        bookService.saveBook(book);
        return new ResponseEntity(status);
    }


    @RequestMapping(value = "/books/new", method = RequestMethod.POST)
    public ResponseEntity addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public HttpStatus deletetBook(@PathVariable("id") Integer bookId) {

        bookService.removeBook(bookId);

        return HttpStatus.OK;
    }


    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Publisher> getPublisher(@PathVariable("id") int id) {
        Optional<Publisher> publisher = Optional.empty();
        HttpStatus status = HttpStatus.OK;
        publisher = publisherService.getPublisherById(id);
        return new ResponseEntity<>(publisher.get(), status);
    }


    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publisher = publisherService.getAllPublishers();
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }


    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.PUT)
    public HttpStatus setPublisher(Publisher publisher){
        publisherService.savePublisher(publisher);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/publishers/new", method = RequestMethod.POST)
    public HttpStatus addPublisher(Publisher publisher) {
        publisherService.addPublisher(publisher);
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.DELETE)
    public HttpStatus removePublisher(@PathVariable("id") int id) {
        publisherService.removePublisher(id);
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/authors/new", method = RequestMethod.POST)
    public HttpStatus createAuthor(Person author) {
        authorService.addAuthor(author);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getAuthor(@PathVariable("id") int id) {
        Person author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllAuthors() {
        List<Person> author = authorService.getAllAuthors();
        return new ResponseEntity<>(author, HttpStatus.OK);
    }


    @RequestMapping(value = "/authors/{id}", method = RequestMethod.PUT)
    public HttpStatus setAuthor(Person author) {
        authorService.saveAuthor(author);
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/authors/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteAuthor(@PathVariable("id") int id) {
        authorService.removeAuthor(id);
        return HttpStatus.OK;
    }


}
