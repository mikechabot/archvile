package com.archvile.mongo;

import com.archvile.Configuration;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Thread-safe MongoDb singleton
 *
 * MongoDb suggests providing the MongoClient as a singleton
 * since the object maintains an internal connection pool
 *
 * http://docs.mongodb.org/ecosystem/drivers/java-concurrency/#java-driver-concurrency
 */
public enum Mongo {

    INSTANCE;

    private MongoClient mongoClient;
    private Configuration config;

    private Mongo() throws ExceptionInInitializerError {
        config = Configuration.INSTANCE;
        try {
            mongoClient = new MongoClient(
                    config.getRequiredString("mongodb-host"),
                    config.getRequiredInt("mongodb-port")
            );
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError("Unable to connect to MongoDb");
        }
    }

    public String getVersion() {
        return mongoClient.getVersion();
    }

    public DB getDb() {
        return mongoClient.getDB(config.getRequiredString("mongodb-dbname"));
    }

}
