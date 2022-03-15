package org.example.schema;

import com.opencsv.bean.CsvBindByPosition;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoCurrencySchema implements Serializable {

    public CryptoCurrencySchema() {
    }

    public CryptoCurrencySchema(String number, String name, String symbol, float marketCap, float price, float circulatingSupply, float volume24hr, float volume1h, float volume24h, float volume7d) {
        Number = number;
        Name = name;
        Symbol = symbol;
        MarketCap = marketCap;
        Price = price;
        CirculatingSupply = circulatingSupply;
        Volume24hr = volume24hr;
        Volume1h = volume1h;
        Volume24h = volume24h;
        Volume7d = volume7d;
    }

    @CsvBindByPosition(position = 0)
    String Number;

    @CsvBindByPosition(position = 1)
    String Name;

    @CsvBindByPosition(position = 2)
    String Symbol;

    @JsonIgnore
    public String getSymbol() {
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
