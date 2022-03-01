package org.example.schema;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonIgnore;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoPricesSchema {
    public static final String ROUTING_KEY = "CURR";

    @CsvBindByPosition(position = 0)
    String Currency;

    @CsvBindByPosition(position = 1)
    long DateTS;

    @JsonIgnore
    public long getTS(){
        return this.DateTS;
    }

    @JsonIgnore
    public String getcurrency(){
        return this.Currency;
    }

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
