package org.example.io.Fixed;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerStats;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.example.io.CsvDataReader;
import org.example.schema.FixedIncomeReturnMacroSchema;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.example.schema.FixedIncomeReturnMacroSchema.ROUTING_KEY;

@Slf4j
public class FixedDataProducer {

    public FixedDataProducer(PulsarClient client, String TopicName,String fixedIncomeFilename) throws IOException, ExecutionException, InterruptedException, CsvValidationException {
        log.info("Initialising Producer<FixedIncomeReturnMacroSchema>");
        Producer<FixedIncomeReturnMacroSchema> pulsarProducerFixedIncome = client.newProducer(JSONSchema.of(FixedIncomeReturnMacroSchema.class))
                .producerName("CryptoProducer")
                .topic(TopicName)
                .enableBatching(Boolean.TRUE)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .batchingMaxMessages(10000)
                .blockIfQueueFull(true)
                .create();

        log.info("Reading CSV file {}", fixedIncomeFilename);
        CsvDataReader cdr = new CsvDataReader();
        Stream<FixedIncomeReturnMacroSchema> cdata = cdr.ReadCSVData(fixedIncomeFilename, FixedIncomeReturnMacroSchema.class);

        log.info("Writing FixedIncomeReturnMacro records to pulsar");
        long startTs = System.currentTimeMillis();
        cdata.forEach(row -> {
            pulsarProducerFixedIncome
                    .newMessage().value(row)
//                    .key(row.getSymbol())
                    .property(ROUTING_KEY, row.getYear())
                    .eventTime(System.currentTimeMillis())
                    .sendAsync()
                    .thenAccept(msgId -> {
                    });

        });
        CompletableFuture<Void> flfuture = pulsarProducerFixedIncome.flushAsync();
        flfuture.get();
        long prodDur = System.currentTimeMillis() - startTs;
        log.info("Final Flush completed");

        ProducerStats stats = pulsarProducerFixedIncome.getStats();
        log.info("Stats num Sent: {} in {} mSec Rate {} TPS", stats.getNumMsgsSent(), prodDur, (stats.getNumMsgsSent() / prodDur) * 1000);
        log.info("Stats TotalSentFailed:" + stats.getTotalSendFailed());
        log.info("Stats Max Latency:" + stats.getSendLatencyMillisMax());
        log.info("Stats SendMsgRate:" + stats.getSendMsgsRate());
        log.info("Stats Total Acks:" + stats.getTotalAcksReceived());

        CompletableFuture<Void> clfuture = pulsarProducerFixedIncome.closeAsync();
        clfuture.get();
//        client.close();
        log.debug("Close completed...");
    }
}