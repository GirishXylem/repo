package com.visenti.test.automation.helpers;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.*;
import com.mongodb.connection.ClusterDescription;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Objects;

public class DatabaseManager {

    private static MongoClient mongoClient;

    private static MongoClient createMongoDBConnection(){
        try{
            String user = ConfigFileReader.getConfigProperty("db.connection.user");
            String database = "config";
            char[] password = Objects.requireNonNull(ConfigFileReader.getConfigProperty("db.connection.password")).toCharArray();
            assert user != null;
            MongoCredential credential = MongoCredential.createCredential(user, database, password);

            MongoClientSettings.Builder optionsBuilder= MongoClientSettings.builder();
            optionsBuilder.wait(5000);
            MongoClientSettings options = optionsBuilder.build();

            mongoClient = new MongoClient(
            ) {
                @Override
                public MongoDatabase getDatabase(String s) {
                    return null;
                }

                @Override
                public ClientSession startSession() {
                    return null;
                }

                @Override
                public ClientSession startSession(ClientSessionOptions clientSessionOptions) {
                    return null;
                }

                @Override
                public void close() {

                }

                @Override
                public MongoIterable<String> listDatabaseNames() {
                    return null;
                }

                @Override
                public MongoIterable<String> listDatabaseNames(ClientSession clientSession) {
                    return null;
                }

                @Override
                public ListDatabasesIterable<Document> listDatabases() {
                    return null;
                }

                @Override
                public ListDatabasesIterable<Document> listDatabases(ClientSession clientSession) {
                    return null;
                }

                @Override
                public <TResult> ListDatabasesIterable<TResult> listDatabases(Class<TResult> aClass) {
                    return null;
                }

                @Override
                public <TResult> ListDatabasesIterable<TResult> listDatabases(ClientSession clientSession, Class<TResult> aClass) {
                    return null;
                }

                @Override
                public ChangeStreamIterable<Document> watch() {
                    return null;
                }

                @Override
                public <TResult> ChangeStreamIterable<TResult> watch(Class<TResult> aClass) {
                    return null;
                }

                @Override
                public ChangeStreamIterable<Document> watch(List<? extends Bson> list) {
                    return null;
                }

                @Override
                public <TResult> ChangeStreamIterable<TResult> watch(List<? extends Bson> list, Class<TResult> aClass) {
                    return null;
                }

                @Override
                public ChangeStreamIterable<Document> watch(ClientSession clientSession) {
                    return null;
                }

                @Override
                public <TResult> ChangeStreamIterable<TResult> watch(ClientSession clientSession, Class<TResult> aClass) {
                    return null;
                }

                @Override
                public ChangeStreamIterable<Document> watch(ClientSession clientSession, List<? extends Bson> list) {
                    return null;
                }

                @Override
                public <TResult> ChangeStreamIterable<TResult> watch(ClientSession clientSession, List<? extends Bson> list, Class<TResult> aClass) {
                    return null;
                }

                @Override
                public ClusterDescription getClusterDescription() {
                    return null;
                }
            };

            String dbConnectionUrl = mongoClient.toString();
            Log.info("Connection to mongo DB - "+ dbConnectionUrl +" is successful");
        }catch (MongoTimeoutException e){
            Log.info("Connection to mongo DB is unsuccessful");
            Log.info("Closing mongo DB connection");
            mongoClient.close();
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mongoClient;
    }

    private static MongoDatabase getMongoDataBase(String database){
        MongoDatabase mongodatabase = createMongoDBConnection().getDatabase(database);
        Log.info(database + " - Database Retrieved Successfully");
        return mongodatabase;
    }

    private static MongoCollection<Document> getMongoCollection(String database, String collection){
        MongoCollection<Document> mongoCollection = getMongoDataBase(database).getCollection(collection);
        Log.info("Inside the collection - " + collection);
        return mongoCollection;
    }

    public static void insertIntoMongoCollection(String database, String collection, Document document) {
        try{
             getMongoCollection(database,collection)
                     .insertOne(document);
            Log.info("Document inserted successfully");

        }catch (Exception e){
            Log.info("Document insert was unsuccessfully");
            e.printStackTrace();
        }finally {
            closeMongoDBConnection();
        }
    }

    private static void closeMongoDBConnection(){
        mongoClient.close();
        Log.info("Successfully terminated mongo DB connection");
    }

}