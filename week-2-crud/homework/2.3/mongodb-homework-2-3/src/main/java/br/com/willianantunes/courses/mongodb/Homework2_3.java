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
		System.out.println("Collection GRADES current size: " + collection.count());
		
		// List<Document> myDocsToBeDelete = new ArrayList<>();

		// https://docs.mongodb.com/manual/reference/operator/aggregation/match/
		// https://docs.mongodb.com/manual/reference/operator/aggregation/eq/
		// https://docs.mongodb.com/manual/reference/method/db.collection.group/
		// https://docs.mongodb.com/manual/reference/operator/aggregation/group/
		
		// db.grades.aggregate( { "$match" : { "type" : "homework" } }, { '$group': { '_id' : { student_id : "$student_id", type : "$type" }, 'min' : { '$min' : '$score' } } },  {'$sort':{'_id':-1}},  {'$limit':1000});
		List<Document> myDocsToBeDelete = collection.aggregate(Arrays.asList(
				new Document("$match", new Document("type", "homework")),
				new Document("$group", 
						new Document("_id", new Document("student_id", "$student_id").append("type", "$type"))
						.append("min", new Document("$min", "$score"))),
				new Document("$sort", new Document("_id", -1)), new Document("$limit", 1000)))
				.into(new ArrayList<Document>())
					.stream().flatMap(d -> Stream.of(new Document("student_id", ((Document)d.get("_id")).get("student_id"))
							.append("type", "homework")
							.append("score", d.get("min"))))
					.collect(Collectors.toList());

		myDocsToBeDelete.forEach(d -> collection.deleteOne(Filters.and(
				Filters.eq("student_id", d.get("student_id")), Filters.eq("type", "homework"),Filters.eq("score", d.get("score")))));
		
		System.out.println("Collection GRADES current size: " + collection.count());
	}
	
	private static void setUpMyDatabase() throws IOException {
		Path myPath = Paths.get("src/main/resources/grades.json");
		List<Document> list = new ArrayList<>();
		Files.readAllLines(myPath).forEach(l -> list.add(Document.parse(l)));

		database.drop();		
		database.getCollection("grades").insertMany(list);
	}
}