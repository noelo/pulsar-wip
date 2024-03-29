/**
 * Copyright 2005-2015 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.example.deprecated;

import org.apache.pulsar.client.api.PulsarClientException;

import java.util.concurrent.ExecutionException;

public class oldMain {

    public static void main(String[] args) throws InterruptedException, PulsarClientException, ExecutionException {
        if (args.length == 0)
            System.out.println("(P)roducer or (C)onsumer");
        else if (args[0].startsWith("P")) {
            PulsarProducer x = new PulsarProducer();
        } else {
            PulsarConsumer x = new PulsarConsumer();
        }
    }
}
