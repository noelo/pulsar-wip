package org.example.crypto;

import com.opencsv.exceptions.CsvValidationException;
import org.example.io.CsvDataReader;
import org.example.schema.CryptoCurrencySchema;
import org.example.schema.CryptoPricesSchema;

import java.io.IOException;
import java.util.stream.Stream;

class CryptoReaderTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void readCCDataPrices() throws CsvValidationException, IOException {
        CsvDataReader x = new CsvDataReader();
        Stream<CryptoPricesSchema> z = x.ReadCSVData("data/crypto/Cryptocurrency_Prices_by_Date.csv", CryptoPricesSchema.class);
        CryptoPricesSchema res = z.findFirst().get();
        System.out.println("lines = "+res);
    }

    @org.junit.jupiter.api.Test
    void readCCDataCurrency() throws CsvValidationException, IOException {
        CsvDataReader x = new CsvDataReader();
        Stream<CryptoCurrencySchema> z = x.ReadCSVData("data/crypto/All_Currencies_Table.csv", CryptoCurrencySchema.class);
        System.out.println("lines = "+z.findFirst().toString());
    }

    @org.junit.jupiter.api.Test
    void readCCDataPricesCount() throws CsvValidationException, IOException {
        CsvDataReader x = new CsvDataReader();
        Stream<CryptoPricesSchema> z = x.ReadCSVData("data/crypto/Cryptocurrency_Prices_by_Date.csv", CryptoPricesSchema.class);
        System.out.println("Count = "+z.count());
    }

    @org.junit.jupiter.api.Test
    void readCCDataCurrencyCount() throws CsvValidationException, IOException {
        CsvDataReader x = new CsvDataReader();
        Stream<CryptoCurrencySchema> z = x.ReadCSVData("data/crypto/All_Currencies_Table.csv", CryptoCurrencySchema.class);
        System.out.println("Count = "+z.count());
    }
}