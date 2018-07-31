package com.books.config;

import com.books.exceptions.UncorrectedQueryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UncorrectedQueryException.class)
    //TODO return json or ...
    //    @ResponseBody
    //    ErrorInfo handleNotFound(HttpServletRequest req, Exception ex) {
    //        return new ErrorInfo(req.getRequestURL().toString(), ex);
    //    }
    // TODO return HTML ?
    public ModelAndView handleNotFound(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

   /* private class ErrorInfo {
        public final String url;
        public final String ex;

        public ErrorInfo(String url, Exception ex) {
            this.url = url;
            this.ex = ex.getLocalizedMessage();
        }
    }*/
}
