package org.example.io.Fixed;

import org.apache.pulsar.client.api.PulsarClient;
import org.example.io.crypto.CryptoCurrencyDataProducer;
import org.example.io.crypto.CryptoPricesDataProducer;

public class FixedIncomeMain {
    public void processFixed(PulsarClient client) throws Exception{
        FixedDataProducer cc = new FixedDataProducer(client, "persistent://public/default/FixedDataTopic", "/home/noelo/dev/noc-pulsar-client/client/data/Fixed/Fixed_income_return_trends_and_macro_trends.csv");
    }

}
