package org.example;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.pulsar.client.impl.schema.JSONSchema;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SchemaTest {

    @org.junit.jupiter.api.Test
    void pricesSchemaTest() throws CsvValidationException, IOException, ExecutionException, InterruptedException {
        JSONSchema<org.example.schema.CryptoPricesSchema> schema = JSONSchema.of(org.example.schema.CryptoPricesSchema.class);
        assertNotNull(schema);
    }
}
