/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
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
 *
 */

package com.mongodb;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Spark;

public class HelloWorldSparkFreemarkerStyle {
	public static void main(String[] args) {
		final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

		Spark.get("/", (request, response) -> {
			StringWriter writer = new StringWriter();
			try {
				Template helloTemplate = configuration.getTemplate("hello.ftl");
				Map<String, Object> helloMap = new HashMap<String, Object>();
				helloMap.put("name", "Freemarker");

				helloTemplate.process(helloMap, writer);
			} catch (Exception e) {
				Spark.halt(500);
				e.printStackTrace();
			}
			return writer;
		});
	}
}