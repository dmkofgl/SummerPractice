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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute("list", bookService.getAllBooks());
        //TODO make it more pretty
        return NavigateServletConstants.BOOK_LIST_JSP_PATH;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewBook(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        List<Person> authors = authorService.getSomeAuthors(book.getAuthors());

        model.addAttribute("publishers", publisherService.getAllPublishers());
        model.addAttribute("canAuthorsAdd", authors);
        model.addAttribute("book", book);
        return NavigateServletConstants.BOOK_EDIT_JSP_PATH;
    }

    @RequestMapping(value = "/{bookId}", params = "form", method = RequestMethod.POST)
    public String update(@PathVariable("id") int id, Book book, BindingResult bindingResult,
                         Model uiModel, HttpServletRequest httpServletRequest) {
        System.out.println(uiModel);
        return "redirect:/contacts/" + book.getId().toString();
    }

    @RequestMapping(value = "/save")
    public String saveBook(Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
        {  return "list";}
        logger.info(book.toString());
        return "list";
    }
}
