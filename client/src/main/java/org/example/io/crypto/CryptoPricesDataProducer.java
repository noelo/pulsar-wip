package org.example.io.crypto;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerStats;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.example.io.CsvDataReader;
import org.example.schema.CryptoPricesSchema;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.example.schema.CryptoPricesSchema.ROUTING_KEY;

@Slf4j
public class CryptoPricesDataProducer {

    public CryptoPricesDataProducer(PulsarClient client, String TopicName, String pricesFilename) throws IOException, ExecutionException, InterruptedException, CsvValidationException {

        log.info("Initialising Producer<CryptoCurrencySchema>");
        Producer<CryptoPricesSchema> pulsarProducerPrices = client.newProducer(JSONSchema.of(CryptoPricesSchema.class))
                .producerName("CryptoProducer")
                .topic(TopicName)
                .enableBatching(Boolean.TRUE)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .batchingMaxMessages(10000)
                .blockIfQueueFull(true)
                .create();

        log.info("Reading CSV file {}",pricesFilename);
        CsvDataReader cdr = new CsvDataReader();
        Stream<CryptoPricesSchema> pdata = cdr.ReadCSVData(pricesFilename,CryptoPricesSchema.class);

        long startTs = System.currentTimeMillis();
        log.info("Writing CryptoPricesSchema records to pulsar");
        pdata.forEach(row -> {
            log.debug("row {}",row);
            pulsarProducerPrices
                    .newMessage().value(row)
                    .key(row.getCurrency())
                    .property(ROUTING_KEY,row.getCurrency())
                    .eventTime(row.getDateTS())
                    .sendAsync()
                    .thenAccept(msgId -> {});

        });
        CompletableFuture<Void> pflfuture = pulsarProducerPrices.flushAsync();
        pflfuture.get();
        long prodDur = System.currentTimeMillis()-startTs;
        log.info("Final Flush completed");

        ProducerStats stats2 = pulsarProducerPrices.getStats();
        log.info("Stats num Sent: {} in {} mSec Rate {} TPS",stats2.getNumMsgsSent(),prodDur,(stats2.getNumMsgsSent()/prodDur)*1000);
        log.info("Stats TotalSentFailed:" + stats2.getTotalSendFailed());
        log.info("Stats Max Latency:" + stats2.getSendLatencyMillisMax());
        log.info("Stats SendMsgRate:" + stats2.getSendMsgsRate());
        log.info("Stats Total Acks:" + stats2.getTotalAcksReceived());

        CompletableFuture<Void> clfuture2 = pulsarProducerPrices.closeAsync();
        clfuture2.get();
//        client.close();
        log.debug("Close completed");
    }
}
