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
package org.apache.camel.component.jms.temp;

import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @version $Revision: 652240 $
 */
public class TemporaryTopicRouteTest extends TemporaryQueueRouteTest {
    private static final transient Log LOG = LogFactory.getLog(TemporaryQueueRouteTest.class);

    @Override
    protected void setUp() throws Exception {
        endpointUri = "activemq:temp:topic:cheese";
        super.setUp();
    }

    @Override
    protected void isValidDestination(Object header) {
        ActiveMQTempTopic destination = assertIsInstanceOf(ActiveMQTempTopic.class, header);
        LOG.info("Received message has a temporary topic: " + destination);
    }
}
