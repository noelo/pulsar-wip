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
import org.apache.pulsar.client.api.PulsarClientException;
import org.example.deprecated.PulsarConsumer;
import org.example.deprecated.PulsarProducer;
import org.example.io.CryptoCurrencyDataProducer;
import org.example.io.CryptoPricesDataProducer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException, CsvValidationException {
            CryptoCurrencyDataProducer cc = new CryptoCurrencyDataProducer("persistent://public/default/currency","data/crypto/All_Currencies_Table.csv");
            CryptoPricesDataProducer cp = new CryptoPricesDataProducer("persistent://public/default/prices","data/crypto/Cryptocurrency_Prices_by_Date.csv");
    }
}
