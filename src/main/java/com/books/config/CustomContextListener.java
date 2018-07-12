package com.books.config;

import com.books.utils.DatabaseCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileNotFoundException;

@Service
public class CustomContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(CustomContextListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
        DatabaseCreator databaseCreator = springContext.getBean(DatabaseCreator.class);
        try {
            databaseCreator.drop("bookapp");
            databaseCreator.create();
        } catch (FileNotFoundException e) {
            logger.info("script files not founded. Scheme create failure");
            databaseCreator.drop("bookapp");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
