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
package org.apache.camel.spi;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.camel.Service;

/**
 * Camel JMX service agent
 */
public interface InstrumentationAgent extends Service {

    /**
     * Registers object with management infrastructure with a specific name. Object must be annotated or 
     * implement standard MBean interface.
     *
     * @param obj  the object to register
     * @param name the name
     * @throws JMException is thrown if the registration failed
     */
    void register(Object obj, ObjectName name) throws JMException;
    
    /**
     * Registers object with management infrastructure with a specific name. Object must be annotated or 
     * implement standard MBean interface.
     *
     * @param obj  the object to register
     * @param name the name
     * @param forceRegistration if set to <tt>true</tt>, then object will be registered despite
     * existing object is already registered with the name.
     * @throws JMException is thrown if the registration failed
     */
    void register(Object obj, ObjectName name, boolean forceRegistration) throws JMException;
    
    /**
     * Unregisters object based upon registered name
     *
     * @param name the name
     * @throws JMException is thrown if the unregistration failed
     */
    void unregister(ObjectName name) throws JMException;

    /**
     * Get the MBeanServer which hosts managed objects.
     * <p/>
     * NOTE: if the JMXEnabled configuration is not set to true, this method will return null.
     * 
     * @return the MBeanServer
     */
    MBeanServer getMBeanServer();

    /**
     * Get domain name for Camel MBeans.  Notice that this can be different that the 
     * default domain name of the MBean Server.  
     * 
     * @return domain name
     */
    String getMBeanObjectDomainName();


}