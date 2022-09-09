package org.example.io.crypto;

import org.apache.pulsar.client.api.PulsarClient;

public class CryptoMain {
    public void processCrypto(PulsarClient client) throws Exception{
        CryptoCurrencyDataProducer cc = new CryptoCurrencyDataProducer(client, "persistent://public/default/currency", "data/crypto/All_Currencies_Table.csv");
        CryptoPricesDataProducer cp = new CryptoPricesDataProducer(client, "persistent://public/default/prices", "data/crypto/Cryptocurrency_Prices_by_Date.csv");
    }

}
