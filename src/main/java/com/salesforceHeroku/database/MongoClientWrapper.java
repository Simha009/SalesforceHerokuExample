package com.salesforceHeroku.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import java.util.List;

public class MongoClientWrapper extends MongoClient {
    public MongoClientWrapper(List<ServerAddress> serverAddresses, List<MongoCredential> mongoCredentials,
                              MongoClientOptions build) {
        super(serverAddresses, mongoCredentials, build);
    }

    public MongoClientWrapper(ServerAddress serverAddress, List<MongoCredential> mongoCredentials,
                              MongoClientOptions build) {
        super(serverAddress, mongoCredentials, build);
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}