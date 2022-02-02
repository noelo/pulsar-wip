package org.example.io;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerStats;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.example.schema.CryptoCurrencySchema;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
public class CryptoCurrencyDataProducer {

    public CryptoCurrencyDataProducer(String TopicName, String currencyFilename) throws IOException, ExecutionException, InterruptedException, CsvValidationException {
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
        Producer<CryptoCurrencySchema> pulsarProducerCurrency = client.newProducer(JSONSchema.of(CryptoCurrencySchema.class))

                .producerName("CryptoProducer")
                .topic(TopicName)
                .enableBatching(Boolean.TRUE)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .batchingMaxMessages(10000)
                .blockIfQueueFull(true)
                .create();

        log.info("Reading CSV file {}",currencyFilename);
        CryptoDataReader cdr = new CryptoDataReader();
        Stream<CryptoCurrencySchema> cdata = cdr.ReadCCData(currencyFilename,CryptoCurrencySchema.class);

        log.info("Reading wrting records to pulsar");
        cdata.forEach(row -> {
            pulsarProducerCurrency
                    .newMessage().value(row)
                    .sendAsync()
                    .thenAccept(msgId -> {});

        });
        CompletableFuture<Void> flfuture = pulsarProducerCurrency.flushAsync();
        flfuture.get();
        log.debug("Final Flush completed");

        ProducerStats stats = pulsarProducerCurrency.getStats();
        log.info("Stats TotalSent:" + stats.getTotalMsgsSent());
        log.info("Stats num Sent:" + stats.getNumMsgsSent());
        log.info("Stats TotalSentFailed:" + stats.getTotalSendFailed());
        log.info("Stats Max Latency:" + stats.getSendLatencyMillisMax());
        log.info("Stats SendMsgRate:" + stats.getSendMsgsRate());
        log.info("Stats Total Acks:" + stats.getTotalAcksReceived());

        CompletableFuture<Void> clfuture = pulsarProducerCurrency.closeAsync();
        clfuture.get();
        log.debug("Close completed");

        client.close();
    }
}
