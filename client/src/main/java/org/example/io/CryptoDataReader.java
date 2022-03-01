package org.example.io;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class CryptoDataReader {

    public <T> Stream<T> ReadCCData(String filename, Class b) throws IOException, CsvValidationException {

        Stream<T> beans = new CsvToBeanBuilder(new FileReader(filename)).withSkipLines(1).withThrowExceptions(Boolean.FALSE)
                .withType(b).build().stream();
        return beans;
    }
}
