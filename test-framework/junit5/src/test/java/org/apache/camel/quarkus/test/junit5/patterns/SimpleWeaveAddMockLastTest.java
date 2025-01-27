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
package org.apache.camel.quarkus.test.junit5.patterns;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.Model;
import org.apache.camel.quarkus.test.CamelQuarkusTestSupport;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestProfile(SimpleWeaveAddMockLastTest.class)
public class SimpleWeaveAddMockLastTest extends CamelQuarkusTestSupport {

    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testWeaveAddMockLast() throws Exception {
        AdviceWith.adviceWith(context.getExtension(Model.class).getRouteDefinitions().get(0), context,
                new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() {
                        weaveAddLast().to("mock:result");
                    }
                });
        context.start();

        getMockEndpoint("mock:result").expectedBodiesReceived("Bye Camel");

        template.sendBody("seda:start", "Camel");

        MockEndpoint.assertIsSatisfied(context);
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("seda:start").transform(simple("Bye ${body}")).to("seda:queue");
            }
        };
    }

}
