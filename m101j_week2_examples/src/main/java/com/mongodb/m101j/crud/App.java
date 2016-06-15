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

import java.util.Arrays;
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
    	
    	MongoCollection<Document> students = null;
        
    	students.find().projection(new Document("phoneNumber", 1).append("_id", 0));
    	students.find().projection(Projections.fields(Projections.include("phoneNumber"), Projections.excludeId()));
    	students.find().projection(Projections.fields(Projections.include("phoneNumber"), Projections.exclude("_id")));
    }
    
}
