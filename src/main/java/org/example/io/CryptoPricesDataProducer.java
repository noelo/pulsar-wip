package org.example.io;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerStats;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.example.io.CryptoDataReader;
import org.example.schema.CryptoPricesSchema;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
public class CryptoPricesDataProducer {

    public CryptoPricesDataProducer(String TopicName, String pricesFilename) throws IOException, ExecutionException, InterruptedException, CsvValidationException {
        log.info("Initialising client");
        PulsarClient client = PulsarClient.builder()
                .allowTlsInsecureConnection(Boolean.TRUE)
                .enableTlsHostnameVerification(Boolean.FALSE)
                .tlsTrustCertsFilePath("./pulsar-proxy-chain.pem")
                .serviceUrl("pulsar+ssl://sslproxy-route-pulsar.apps.ocp.themadgrape.com:443")
                .enableTcpNoDelay(Boolean.TRUE)
                .statsInterval(5, TimeUnit.MINUTES)
                .build();

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
        CryptoDataReader cdr = new CryptoDataReader();
        Stream<CryptoPricesSchema> pdata = cdr.ReadCCData(pricesFilename,CryptoPricesSchema.class);

        log.info("Reading wrting records to pulsar");
        pdata.forEach(row -> {
            pulsarProducerPrices
                    .newMessage().value(row)
                    .sendAsync()
                    .thenAccept(msgId -> {});

        });
        CompletableFuture<Void> pflfuture = pulsarProducerPrices.flushAsync();
        pflfuture.get();
        log.debug("Final Flush completed");

        ProducerStats stats2 = pulsarProducerPrices.getStats();
        log.info("Stats TotalSent:" + stats2.getTotalMsgsSent());
        log.info("Stats num Sent:" + stats2.getNumMsgsSent());
        log.info("Stats TotalSentFailed:" + stats2.getTotalSendFailed());
        log.info("Stats Max Latency:" + stats2.getSendLatencyMillisMax());
        log.info("Stats SendMsgRate:" + stats2.getSendMsgsRate());
        log.info("Stats Total Acks:" + stats2.getTotalAcksReceived());

        CompletableFuture<Void> clfuture2 = pulsarProducerPrices.closeAsync();
        clfuture2.get();
        log.debug("Close completed");

        client.close();
    }
}
