/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.m101j.crud;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.m101j.util.Helpers;

import static com.mongodb.m101j.util.Helpers.printJson;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.BsonDocument;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
    	/*
    	Document document = new Document("_id", "user1")
    			.append("interests", Arrays.asList("basketball", "drumming"));
    	
    	Helpers.printJson(document);
    	*/

    	/*
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("school");
		MongoCollection<Document> people = database.getCollection("people");

		Document doc = new Document("name", "Andrew Erlichson").append("company", "10gen");

		Helpers.printJson(doc);
		people.insertOne(doc); // first insert
		Helpers.printJson(doc);
		doc.remove("_id"); // remove the _id key
		Helpers.printJson(doc);
		people.insertOne(doc); // second insert
		Helpers.printJson(doc);
		*/
    	
    	/*
    	MongoClient client = new MongoClient();
    	MongoDatabase database = client.getDatabase("school");
    	MongoCollection<Document> people = database.getCollection("people");
    	Document doc;
    	doc = people.find().first();
    	System.out.println(doc);
    	*/
    	
    	/*
    	MongoCollection<Document> collection = null;
        
        collection.find(Filters.and(Filters.eq("type", "quiz"), Filters.gt("score", 20), Filters.lt("score", 90)));
        collection.find(new Document("type", "quiz").append("score", new Document("$gt", 20).append("$lt", 90)));
        */
    	
    	/*
    	MongoCollection<Document> students = null;
        
    	students.find().projection(new Document("phoneNumber", 1).append("_id", 0));
    	students.find().projection(Projections.fields(Projections.include("phoneNumber"), Projections.excludeId()));
    	students.find().projection(Projections.fields(Projections.include("phoneNumber"), Projections.exclude("_id")));
    	*/
    	
    	/*
    	MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("insertTestQuizSortSkipLimit");
        collection.drop();
        
    	Document id0 = new Document("_id", 0).append("value", 10);
    	Document id1 = new Document("_id", 1).append("value", 5);
    	Document id2 = new Document("_id", 2).append("value", 7);
    	Document id3 = new Document("_id", 3).append("value", 20);
    	
    	collection.insertMany(asList(id0, id1, id2, id3));
    	 
    	List<Document> all = collection.find().sort(new Document("value", -1)).skip(2).limit(1).into(new ArrayList<Document>());
    	all.forEach(i -> printJson(i));
    	*/
    	
    	/*
    	MongoCollection<Document> scores = null;
    	
    	scores.updateOne(new Document("_id", 1), new Document("$set", new Document("examiner", "Jones")));
    	scores.updateOne(Filters.eq("_id", 1), new Document("$set", new Document("examiner", "Jones")));
    	*/
    	
       	MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("insertTestQuizDelete");
        collection.drop();
        
    	Document id0 = new Document("_id", 0).append("x", 1);
    	Document id1 = new Document("_id", 1).append("x", 1);
    	Document id2 = new Document("_id", 2).append("x", 1);
    	Document id3 = new Document("_id", 3).append("x", 2);
    	Document id4 = new Document("_id", 4).append("x", 2);
    	
    	collection.insertMany(asList(id0, id1, id2, id3, id4));
    	collection.deleteOne(Filters.eq("x", 1));
    	collection.find().into(new ArrayList<Document>()).forEach(i -> printJson(i));;
    }
    
}
