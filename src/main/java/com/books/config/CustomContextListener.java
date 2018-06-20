package com.books.config;

import com.books.utils.DatabaseCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileNotFoundException;

public class CustomContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(CustomContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            new DatabaseCreator().drop("bookapp");
            new DatabaseCreator().create();
        } catch (FileNotFoundException e) {
            logger.info("script files not founded. Scheme doesn't create falure");
            new DatabaseCreator().drop("bookapp");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
