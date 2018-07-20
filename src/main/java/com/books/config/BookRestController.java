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

@RestController
@RequestMapping("/rest")
public class BookRestController {
    @Autowired
    BookService bookService;
    PublisherService publisherService;
    AuthorService authorService;

    /**
     * Book
     * READ
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * Book
     * READ
     *
     * @param id book id
     * @return book by id
     */
    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        //TODO if not ok
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    /**
     * Book
     * UPDATE
     *
     * @param book book, that need to save
     * @return operation result status
     */
    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public HttpStatus setBook(Book book) {
        HttpStatus status = HttpStatus.OK;
        bookService.saveBook(book);
        //TODO if not ok
        return status;
    }

    /**
     * Book
     * CREATE
     *
     * @param book book, that need to add
     * @return operation result status
     */
    @RequestMapping(value = "/books/new", method = RequestMethod.POST)
    public HttpStatus addBook(Book book) {
        bookService.addBook(book);
        //TODO if not ok
        return HttpStatus.CREATED;
    }

    /**
     * Book
     * DELETE
     *
     * @param bookId id book that will be delete
     * @return operation result status
     */
    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public HttpStatus deletetBook(@PathVariable("id") Integer bookId) {
        bookService.removeBook(bookId);
        //TODO if not ok
        return HttpStatus.OK;
    }

    /**
     * Publisher
     * READ
     *
     * @param id id publisher, that need to take
     * @return publisher by id
     */
    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Publisher> getPublisher(@PathVariable("id") int id) {
        Publisher publisher = publisherService.getPublisherById(id).get();
        //TODO if not ok
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    /**
     * Publisher
     * READ
     *
     * @return
     */
    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publisher = publisherService.getAllPublishers();
        //TODO if not ok
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    /**
     * Publisher
     * UPDATE
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.PUT)
    public HttpStatus setPublisher(Publisher publisher) {
        publisherService.savePublisher(publisher);
        //TODO if not ok
        return HttpStatus.OK;
    }

    /**
     * Publisher
     * CREATE
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/publishers/new", method = RequestMethod.POST)
    public HttpStatus addPublisher(Publisher publisher) {
        publisherService.addPublisher(publisher);
        //TODO if not ok
        return HttpStatus.OK;
    }

    /**
     * Publisher
     * DELETE
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/publishers/{id}", method = RequestMethod.DELETE)
    public HttpStatus addPublisher(@PathVariable("id") int id) {
        publisherService.removePublisher(id);
        //TODO if not ok
        return HttpStatus.OK;
    }

    /**
     * Author
     * CREATE
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/authors/new", method = RequestMethod.POST)
    public HttpStatus createAuthor(Person author) {
        authorService.addAuthor(author);
        //TODO if not ok
        return HttpStatus.OK;
    }

    /**
     * Author
     * READ
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getAuthor(@PathVariable("id") int id) {
        Person author = authorService.getAuthorById(id);
        //TODO if not ok
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /**
     * Author
     * READ
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllAuthors() {
        List<Person> author = authorService.getAllAuthors();
        //TODO if not ok
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /**
     * Author
     * UPDATE
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/authors/{id}", method = RequestMethod.PUT)
    public HttpStatus setAuthor(Person author) {
        authorService.saveAuthor(author);
        //TODO if not ok
        return HttpStatus.OK;
    }


    /**
     * Author
     * DELETE
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/authors/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteAuthor(@PathVariable("id") int id) {
        authorService.removeAuthor(id);
        //TODO if not ok
        return HttpStatus.OK;
    }


}
