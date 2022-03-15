package org.example.schema;

import com.opencsv.bean.CsvBindByPosition;
import org.apache.pulsar.shade.com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CryptoPricesSchema implements Serializable {
    public static final String ROUTING_KEY = "CURR";

    public CryptoPricesSchema(String currency, long dateTS, float price) {
        Currency = currency;
        DateTS = dateTS;
        Price = price;
    }

    public CryptoPricesSchema() {
    }

    @CsvBindByPosition(position = 0)
    String Currency;

    @CsvBindByPosition(position = 1)
    long DateTS;

    @CsvBindByPosition(position = 2)
    float Price;

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public long getDateTS() {
        return DateTS;
    }

    public void setDateTS(long dateTS) {
        DateTS = dateTS;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "CryptoPricesSchema{" +
                "Currency='" + Currency + '\'' +
                ", DateTS=" + DateTS +
                ", Price=" + Price +
                '}';
    }
}
