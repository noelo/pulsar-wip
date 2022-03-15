package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.example.io.CryptoCurrencyDataProducer;
import org.example.io.CryptoPricesDataProducer;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

class CryptoDataProducerTest {

    @org.junit.jupiter.api.Test
    void currencyTest() throws CsvValidationException, IOException, ExecutionException, InterruptedException {
        CryptoCurrencyDataProducer x = new CryptoCurrencyDataProducer(null,"persistent://public/default/currency","data/crypto/All_Currencies_Table.csv");
    }


    @org.junit.jupiter.api.Test
    void pricesTest() throws CsvValidationException, IOException, ExecutionException, InterruptedException {
        CryptoPricesDataProducer x = new CryptoPricesDataProducer(null,"persistent://public/default/prices","data/crypto/Cryptocurrency_Prices_by_Date.csv");
    }
}