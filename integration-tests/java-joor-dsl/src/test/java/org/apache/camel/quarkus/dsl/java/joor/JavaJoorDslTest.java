/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.quarkus.dsl.java.joor;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.apache.camel.dsl.java.joor.JavaRoutesBuilderLoader;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
class JavaJoorDslTest {

    @Test
    void joorHello() {
        RestAssured.given()
                .body("Camelus bactrianus")
                .post("/java-joor-dsl/hello")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("Hello Camelus bactrianus from jOOR!"));
    }

    @Test
    void testMainInstanceWithJavaRoutes() {
        RestAssured.given()
                .get("/java-joor-dsl/main/javaRoutesBuilderLoader")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is(JavaRoutesBuilderLoader.class.getName()));

        RestAssured.given()
                .get("/java-joor-dsl/main/routeBuilders")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is(""));

        RestAssured.given()
                .get("/java-joor-dsl/main/routes")
                .then()
                .statusCode(200)
                .body(CoreMatchers.is("my-java-route"));
    }
}
