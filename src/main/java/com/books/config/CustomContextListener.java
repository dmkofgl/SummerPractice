package com.books.config;

import com.books.utils.DatabaseCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class CustomContextListener implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CustomContextListener.class);

    @Autowired
    private DatabaseCreator databaseCreator;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            databaseCreator.drop("bookapp");
            databaseCreator.create();
        } catch (FileNotFoundException e) {
            logger.info("script files not founded. Scheme create failure");
            databaseCreator.drop("bookapp");
        }
    }
}
