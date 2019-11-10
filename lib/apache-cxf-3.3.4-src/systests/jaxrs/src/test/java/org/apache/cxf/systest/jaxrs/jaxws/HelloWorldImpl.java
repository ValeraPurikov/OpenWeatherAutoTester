/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// START SNIPPET: service
package org.apache.cxf.systest.jaxrs.jaxws;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.jws.WebService;

@WebService(endpointInterface = "org.apache.cxf.systest.jaxrs.jaxws.HelloWorld",
            serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    Map<Integer, User> users = new LinkedHashMap<>();

    public int clearUsers() {
        int i = users.size();
        users.clear();
        return i;
    }


    public String sayHi(String text) {
        return "Hello " + text;
    }

    public String sayHiToUser(User user) {
        users.put(users.size() + 1, user);
        return "Hello "  + user.getName();
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public Map<Integer, User> echoUsers(Map<Integer, User> us) {
        if (!users.equals(us)) {
            throw new RuntimeException();
        }
        return us;
    }

    public User echoUser(User user) {
        return user;
    }

}
// END SNIPPET: service
