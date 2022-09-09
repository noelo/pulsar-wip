package org.example.Fixed;

import com.opencsv.exceptions.CsvValidationException;
import org.example.io.CsvDataReader;
import org.example.schema.CryptoCurrencySchema;
import org.example.schema.CryptoPricesSchema;
import org.example.schema.FixedIncomeReturnMacroSchema;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedReaderTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void readCCFixedPrices() throws CsvValidationException, IOException {
        CsvDataReader x = new CsvDataReader();
        Stream<FixedIncomeReturnMacroSchema> z = x.ReadCSVData("data/Fixed/Fixed_income_return_trends_and_macro_trends.csv", FixedIncomeReturnMacroSchema.class);
        FixedIncomeReturnMacroSchema res = z.findFirst().get();
        System.out.println("lines = "+res);
    }

    @org.junit.jupiter.api.Test
    void readCCFixedPricesGetDate() throws CsvValidationException, IOException {
        CsvDataReader x = new CsvDataReader();
        Stream<FixedIncomeReturnMacroSchema> z = x.ReadCSVData("data/Fixed/Fixed_income_return_trends_and_macro_trends.csv", FixedIncomeReturnMacroSchema.class);
        FixedIncomeReturnMacroSchema res = z.findFirst().get();
        String year = res.getYear();
        assertEquals("2000",year);
    }

}