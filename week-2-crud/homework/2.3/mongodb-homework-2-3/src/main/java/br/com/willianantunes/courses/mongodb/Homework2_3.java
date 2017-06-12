package br.com.willianantunes.courses.mongodb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Homework2_3 {
    private static MongoClient client = new MongoClient();
    private static MongoDatabase database = client.getDatabase("students");
    private static MongoCollection<Document> collection = database.getCollection("grades");
	
	public static void main(String args[]) throws IOException {
		/**
		 * Write a program in the language of your choice that will remove the grade of type 
		 * "homework" with the lowest score for each student from the dataset in the handout. 
		 * Since each document is one grade, it should remove one document per student. This will 
		 * use the same data set as the last problem, but if you don't have it, you can download and re-import.
		 */
		setUpMyDatabase();
		
		/*
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("students");
        MongoCollection<Document> collection = database.getCollection("grades");
        */
        // database.c

	    
	}
	
	private static void setUpMyDatabase() throws IOException {
		Path myPath = Paths.get("src/main/resources/grades.json");
		List<Document> list = new ArrayList<>();
		Files.readAllLines(myPath).forEach(l -> list.add(Document.parse(l)));

		database.drop();		
		database.getCollection("grades").insertMany(list);
		System.out.println(collection.count());
	}
}