package org.example.schema;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonAutoDetect;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoPricesSchema {

    @CsvBindByPosition(position = 0)
    String Currency;


    @CsvBindByPosition(position = 1)
    long DateTS;


    @CsvBindByPosition(position = 2)
    float Price;

    @Override
    public String toString() {
        return "CryptoPricesSchema{" +
                "Currency='" + Currency + '\'' +
                ", DateTS=" + DateTS +
                ", Price=" + Price +
                '}';
    }
}
