package com.books.config;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.services.abstracts.AuthorServiceable;
import com.books.services.abstracts.BookServiceable;
import com.books.services.abstracts.PublisherServiceable;
import com.books.utils.DateFormatter;
import com.books.utils.NavigateServletConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.text.DateFormat;
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
        String query = "";
        model.addAttribute("query", query);
        model.addAttribute("books", bookService.getAllBooks());
        return "search";
    }

    @PostMapping("/search")
    public String filterByAuthor(@RequestParam("query") String query, Model model, HttpServletRequest request) {
        // String query = request.getParameter("query");
        model.addAttribute("books", bookService.filterByAuthorName(query));
        model.addAttribute("query", query);
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


    @RequestMapping(value = "/{bookId}", method = RequestMethod.POST)
    public String removeAuthor(@RequestParam("removeAuthor") Integer authorId, Book book, Model model) {

        book.removeAuthor(authorId);
        List<Person> authors = authorService.getSomeAuthors(book.getAuthors());
        model.addAttribute("publishers", publisherService.getAllPublishers());
        model.addAttribute("canAuthorsAdd", authors);
        model.addAttribute("book", book);
        return "edit";
    }

    @RequestMapping(value = "/save")
    public String saveBook(Book book, BindingResult bindingResult, Model model) {
        bookService.saveBook(book);
        return "redirect:list";
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
        dataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                try {

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(value);
                    setValue(date);
                } catch (ParseException e) {
                    setValue(null);
                    logger.error(e.getMessage());
                }
            }
        });

    }

}
