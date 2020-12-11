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
package org.apache.camel.quarkus.component.debezium.common.it;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/debezium-mongodb")
@ApplicationScoped
public class DebeziumMongodbResource extends AbstractDebeziumResource {

    public DebeziumMongodbResource() {
        super(Type.mongodb);
    }

    @Path("/receiveAsRecord")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Record receiveAsRecord() {
        return super.receiveAsRecord();
    }

    @Path("/receive")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String receive() {
        return super.receive();
    }

    @Path("/receiveOperation")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String receiveOperation() {
        Record record = receiveAsRecord();

        if (record == null) {
            return null;
        }
        return record.getOperation();
    }

    @Override
    String getEndpoinUrl(String hostname, String port, String username, String password, String databaseServerName,
            String offsetStorageFileName) {
        return Type.mongodb.getComponent() + ":localhost?"
                + "offsetStorageFileName=" + offsetStorageFileName
                + "&mongodbUser=" + System.getProperty(Type.mongodb.getPropertyUsername())
                + "&mongodbPassword=" + System.getProperty(Type.mongodb.getPropertyPassword())
                + "&mongodbName=docker-rs"
                + "&mongodbHosts=" + hostname + ":" + port;
    }
}
