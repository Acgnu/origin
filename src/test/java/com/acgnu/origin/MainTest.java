package com.acgnu.origin;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MainTest {
    public static void main(String[] args) {
        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase testDB = mongoClient.getDatabase("test");
            MongoCollection collection = testDB.getCollection("table");

            Document row = new Document("k1", "v1");
            row.append("k1", "v1-1").append("k2", "v2");
            collection.insertOne(row);
        } catch (Exception e) {

        }
    }
}
