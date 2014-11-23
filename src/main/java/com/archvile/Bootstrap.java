package com.archvile;

import com.archvile.mongo.Mongo;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Bootstrap implements ServletContextListener {

    private static Logger log = Logger.getLogger(Bootstrap.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Initializing archvile...");

        /* Connect to mongo */
        Mongo mongo = Mongo.INSTANCE;
        log.info("MongoDb version: " + mongo.getVersion());

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Destroying archvile...");
    }
}
