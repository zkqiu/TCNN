/**
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
package org.apache.camel.spring.processor;

import org.apache.camel.CamelContext;
import org.apache.camel.ContextTestSupport;
import org.apache.camel.component.mock.MockEndpoint;

import static org.apache.camel.spring.processor.SpringTestHelper.createSpringCamelContext;

public class SpringSetHeaderWithChildProcessorTest extends ContextTestSupport {
    
    public void testSetHeaderWithExpression() throws Exception {
        MockEndpoint resultEndpoint = getMockEndpoint("mock:b");
        resultEndpoint.expectedBodiesReceived("Hello");
        resultEndpoint.expectedHeaderReceived("oldBodyValue", "Hello");

        sendBody("seda:a", "Hello");

        resultEndpoint.assertIsSatisfied();
    }

    protected CamelContext createCamelContext() throws Exception {
        return createSpringCamelContext(this, "org/apache/camel/spring/processor/SpringSetHeaderTestWithChildProcessor-context.xml");
    }
}
