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
package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.pulsar.client.api.PulsarClient;
import org.example.io.Fixed.FixedIncomeMain;
import org.example.io.crypto.CryptoCurrencyDataProducer;
import org.example.io.crypto.CryptoMain;
import org.example.io.crypto.CryptoPricesDataProducer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException, CsvValidationException {
        PulsarClient client = PulsarClient.builder()
                .allowTlsInsecureConnection(Boolean.TRUE)
                .enableTlsHostnameVerification(Boolean.FALSE)
                .tlsTrustCertsFilePath("/home/noelo/dev/noc-pulsar-client/client/certs/pulsar-proxy.pem")
                .serviceUrl("pulsar+ssl://sslproxy-route-pulsar.apps.ocp.sno.themadgrape.com:443")
                .enableTcpNoDelay(Boolean.TRUE)
                .statsInterval(5, TimeUnit.MINUTES)
                .build();
        try {
//            CryptoMain crypto = new CryptoMain();
//            crypto.processCrypto(client);

            FixedIncomeMain fix = new FixedIncomeMain();
            fix.processFixed(client);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            client.close();
        }
    }
}
