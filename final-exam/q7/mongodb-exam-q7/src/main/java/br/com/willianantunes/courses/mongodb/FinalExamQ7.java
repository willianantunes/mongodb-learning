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
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.Updates;

public class FinalExamQ7 {
    private static MongoClient client = new MongoClient();
    private static MongoDatabase database = client.getDatabase("examq7");
    private static MongoCollection<Document> myAlbumsCollection = database.getCollection("albums");
    private static MongoCollection<Document> myImagesCollection = database.getCollection("images");
	
	public static void main(String args[]) throws IOException, InterruptedException {
		setUpMyDatabase();
		System.out.println("Collection ALBUMS current size: " + myAlbumsCollection.count());
		System.out.println("Collection IMAGES current size: " + myImagesCollection.count());

	}
	
	// In order to avoid 1000 limit as explained here: https://docs.mongodb.com/manual/reference/limits/#Write-Command-Operation-Limit-Size
	private static void setUpMyDatabase() throws IOException {
		database.drop();

		Path myPath = Paths.get("src/main/resources/albums.json");
		List<Document> albumList = new ArrayList<>();
		Files.readAllLines(myPath).forEach(l -> {
			albumList.add(Document.parse(l));
			if (albumList.size() % 1000 == 0) {
				myAlbumsCollection.insertMany(albumList);
				albumList.clear();
			}			
		});

		myPath = Paths.get("src/main/resources/images.json");
		List<Document> imageList = new ArrayList<>();
		Files.readAllLines(myPath).forEach(l -> { 
			imageList.add(Document.parse(l)); 
			if (imageList.size() % 1000 == 0) {
				myImagesCollection.insertMany(imageList);
				imageList.clear();
			}			
		});
	}
}