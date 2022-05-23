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
package org.apache.camel.quarkus.component.xchange.it;

import java.util.Map;

import org.apache.camel.quarkus.test.mock.backend.MockBackendUtils;
import org.apache.camel.quarkus.test.wiremock.WireMockTestResourceLifecycleManager;

public class XchangeKrakenTestResource extends WireMockTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        Map<String, String> options = super.start();
        String wireMockUrl = options.get("wiremock.url");
        if (wireMockUrl != null) {
            options.put("wiremock.kraken.url", wireMockUrl);
        }
        return options;
    }

    @Override
    protected String getRecordTargetBaseUrl() {
        return "https://api.kraken.com/";
    }

    @Override
    protected boolean isMockingEnabled() {
        return MockBackendUtils.startMockBackend(false);
    }
}
