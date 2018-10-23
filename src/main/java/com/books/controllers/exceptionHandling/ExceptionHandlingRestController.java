package com.books.controllers.exceptionHandling;

import com.books.controllers.BookRestController;
import com.books.exceptions.UncorrectedQueryException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@ControllerAdvice(assignableTypes = BookRestController.class)
public class ExceptionHandlingRestController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UncorrectedQueryException.class)
    @ResponseBody
    ErrorInfo handleNotFound(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

    private class ErrorInfo {
        public final String url;
        public final String ex;

        public ErrorInfo(String url, Exception ex) {
            this.url = url;
            this.ex = ex.getLocalizedMessage();
        }
    }
}
