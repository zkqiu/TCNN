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
package org.apache.camel.loanbroker.queue.version;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;
//START SNIPPET: client
public class Client extends RouteBuilder {

    public static void main(String args[]) throws Exception {
        CamelContext context = new DefaultCamelContext();
        // Set up the ActiveMQ JMS Components
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        // Note we can explicit name of the component
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new Client());

        ProducerTemplate template = context.createProducerTemplate();

        context.start();

        // send out the request message
        for (int i = 0; i < 2; i++) {
            template.sendBodyAndHeader("jms:queue:loanRequestQueue",
                                       "Quote for the lowerst rate of loaning bank",
                                       Constants.PROPERTY_SSN, "Client-A" + i);
            Thread.sleep(100);
        }
        // wait for the response
        Thread.sleep(2000);
        
        // send the request and get the response from the same queue       
        Exchange exchange = template.send("jms:queue2:parallelLoanRequestQueue", new Processor() {
            public void process(Exchange exchange) throws Exception {
                exchange.setPattern(ExchangePattern.InOut);
                exchange.getIn().setBody("Quote for the lowerst rate of loaning bank");
                exchange.getIn().setHeader(Constants.PROPERTY_SSN, "Client-B");
            }
        });
        
        String bank = (String)exchange.getOut().getHeader(Constants.PROPERTY_BANK);
        Double rate = (Double)exchange.getOut().getHeader(Constants.PROPERTY_RATE);
        String ssn = (String)exchange.getOut().getHeader(Constants.PROPERTY_SSN);
        System.out.println("Loan quotion for Client " + ssn + "."
                           + " The lowest rate bank is " + bank + ", the rate is " + rate);
        
        // Wait a while before stop the context
        Thread.sleep(1000 * 5);
        context.stop();

    }

    /**
     * Lets configure the Camel routing rules using Java code to pull the response message
     */
    public void configure() {

        from("jms:queue:loanReplyQueue").process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                // Print out the response message
                System.out.println(exchange.getIn().getBody());

            }

        });

    }

}
// END SNIPPET: client
