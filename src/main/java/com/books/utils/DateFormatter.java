package com.books.utils;

import javafx.scene.input.DataFormat;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String s, Locale locale) throws ParseException {
       Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
       return  date;
    }

    @Override
    public String print(Date date, Locale locale) {
       String res =String.format("%f",date);
       return  res;
    }
}
