package br.com.willianantunes.courses.mongodb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class Homework3_1 {
    private static MongoClient client = new MongoClient();
    private static MongoDatabase database = client.getDatabase("school");
    private static MongoCollection<Document> collection = database.getCollection("students");
	
	public static void main(String args[]) throws IOException {
		/**
		 * Write a program in the language of your choice that will remove the lowest homework score 
		 * for each student. Since there is a single document for each student containing an 
		 * array of scores, you will need to update the scores array and remove the homework.
		 */
		setUpMyDatabase();
		System.out.println("Collection STUDENTS current size: " + collection.count());
		
		// List<Document> myDocsToBeDelete = new ArrayList<>();

		// https://docs.mongodb.com/manual/reference/operator/aggregation/unwind/
		
		// db.students.aggregate([ { $unwind: "$scores" }, { $match : { "scores.type" : "homework" } }, { $group: { '_id' : { _id : "$_id", type: "$scores.type" }, 'min' : { '$min' : '$scores.score' } } }, {'$sort':{'_id':-1}} ]);
		List<Document> myDocsToBeDelete = collection.aggregate(Arrays.asList(
				new Document("$unwind", "$scores"),
				new Document("$match", new Document("scores.type", "homework")),
				new Document("$group", 
						new Document("_id", new Document("_id", "$_id").append("type", "$scores.type"))
						.append("min", new Document("$min", "$scores.score"))),
				new Document("$sort", new Document("_id", -1))))
				.into(new ArrayList<Document>());
		
		// Document{{_id=Document{{_id=199, type=homework}}, min=5.861613903793295}}
		myDocsToBeDelete.stream().forEach(d -> {
			collection.updateMany(Filters.eq("_id", ((Document)d.get("_id")).get("_id")), Updates.pull("scores", 
					Filters.and(Filters.eq("type", "homework"), Filters.eq("score", d.get("min")))));
		});

		System.out.println("Collection STUDENTS current size: " + collection.count());
	}
	
	private static void setUpMyDatabase() throws IOException {
		Path myPath = Paths.get("src/main/resources/students.json");
		List<Document> list = new ArrayList<>();
		Files.readAllLines(myPath).forEach(l -> list.add(Document.parse(l)));

		database.drop();		
		database.getCollection("students").insertMany(list);
	}
}