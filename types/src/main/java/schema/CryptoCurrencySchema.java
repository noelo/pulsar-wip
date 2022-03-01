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
public class CryptoCurrencySchema {

    @CsvBindByPosition(position = 0)
    String Number;

    @CsvBindByPosition(position = 1)
    String Name;

    @CsvBindByPosition(position = 2)
    String Symbol;

    @JsonIgnore
    public String getSymbol(){
        return this.Symbol;
    }

    @CsvBindByPosition(position = 3)
    float MarketCap;

    @CsvBindByPosition(position = 4)
    float Price;

    @CsvBindByPosition(position = 5)
    float CirculatingSupply;

    @CsvBindByPosition(position = 6)
    float Volume24hr;

    @CsvBindByPosition(position = 7)
    float Volume1h;

    @CsvBindByPosition(position = 8)
    float Volume24h;

    @CsvBindByPosition(position = 9)
    float Volume7d;

    @Override
    public String toString() {
        return "CryptoCurrencySchema{" +
                "Number='" + Number + '\'' +
                ", Name='" + Name + '\'' +
                ", Symbol='" + Symbol + '\'' +
                ", MarketCap=" + MarketCap +
                ", Price=" + Price +
                ", CirculatingSupply=" + CirculatingSupply +
                ", Volume24hr=" + Volume24hr +
                ", Volume1h=" + Volume1h +
                ", Volume24h=" + Volume24h +
                ", Volume7d=" + Volume7d +
                '}';
    }
}
