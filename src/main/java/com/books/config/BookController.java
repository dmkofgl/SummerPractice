package com.books.config;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.services.abstracts.AuthorServiceable;
import com.books.services.abstracts.BookServiceable;
import com.books.services.abstracts.PublisherServiceable;
import com.books.utils.NavigateServletConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @GetMapping("/list")
    public String viewBookList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        //TODO make it more pretty
        return "list";
    }
    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "search";
    }
    @PostMapping("/search")
    public String filterByAuthor(String part, Model model) {
        model.addAttribute("books", bookService.filterByAuthorName(part));
        return "search";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewBook(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        List<Person> authors = authorService.getSomeAuthors(book.getAuthors());

        model.addAttribute("publishers", publisherService.getAllPublishers());
        model.addAttribute("canAuthorsAdd", authors);
        model.addAttribute("book", book);
        return "edit";
    }

    @RequestMapping(value = "/{bookId}", params = "form", method = RequestMethod.POST)
    public String update(@PathVariable("id") int id, Book book, BindingResult bindingResult,
                         Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println(uiModel);
        return "list";
    }

    @RequestMapping(value = "/save")
    public String saveBook(Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "list";
        }
        logger.info(book.toString());
        return "list";
    }

    @RequestMapping(value = "/hello")
    public String hello(Model model) {
        model.addAttribute("book", bookService.getBookById(1));
        return "hello";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String save(@ModelAttribute Book book, Model model) {
        model.addAttribute("book", bookService.getBookById(1));
        logger.info(book.toString());
        return "hello";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                try {
                    setValue(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                } catch (ParseException e) {
                    setValue(null);
                    logger.error(e.getMessage());
                }
            }
        });

    }

}
