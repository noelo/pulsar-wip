package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.example.io.CryptoDataReader;
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
        CryptoDataReader x = new CryptoDataReader();
        Stream<CryptoPricesSchema> z = x.ReadCCData("data/crypto/Cryptocurrency_Prices_by_Date.csv", CryptoPricesSchema.class);
        CryptoPricesSchema res = z.findFirst().get();
        System.out.println("lines = "+res);
    }

    @org.junit.jupiter.api.Test
    void readCCDataCurrency() throws CsvValidationException, IOException {
        CryptoDataReader x = new CryptoDataReader();
        Stream<CryptoCurrencySchema> z = x.ReadCCData("data/crypto/All_Currencies_Table.csv", CryptoCurrencySchema.class);
        System.out.println("lines = "+z.findFirst().toString());
    }

    @org.junit.jupiter.api.Test
    void readCCDataPricesCount() throws CsvValidationException, IOException {
        CryptoDataReader x = new CryptoDataReader();
        Stream<CryptoPricesSchema> z = x.ReadCCData("data/crypto/Cryptocurrency_Prices_by_Date.csv", CryptoPricesSchema.class);
        System.out.println("Count = "+z.count());
    }

    @org.junit.jupiter.api.Test
    void readCCDataCurrencyCount() throws CsvValidationException, IOException {
        CryptoDataReader x = new CryptoDataReader();
        Stream<CryptoCurrencySchema> z = x.ReadCCData("data/crypto/All_Currencies_Table.csv", CryptoCurrencySchema.class);
        System.out.println("Count = "+z.count());
    }
}