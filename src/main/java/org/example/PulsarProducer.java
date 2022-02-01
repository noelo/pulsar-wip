package org.example;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerStats;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PulsarProducer {
    public PulsarProducer() throws PulsarClientException, ExecutionException, InterruptedException {
        System.out.println("firing up client");

        PulsarClient client = PulsarClient.builder()
                .allowTlsInsecureConnection(Boolean.TRUE)
                .enableTlsHostnameVerification(Boolean.FALSE)
                .tlsTrustCertsFilePath("./pulsar-proxy-chain.pem")
                .serviceUrl("pulsar+ssl://sslproxy-route-pulsar.apps.ocp.themadgrape.com:443")
                .enableTcpNoDelay(Boolean.TRUE)
                .statsInterval(5, TimeUnit.MINUTES)
                .build();

        Producer<byte[]> stringProducer = client.newProducer()
                .producerName("testProducer")
                .topic("persistent://public/default/my-topic")
                .enableBatching(Boolean.TRUE)
                .batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
                .batchingMaxMessages(10000)
                .blockIfQueueFull(true)
                .create();

        long curr = System.currentTimeMillis() - 1;
        long start = curr;
        float tps = 0;
        System.out.println("Starting producer @ " + start);
        for (int i = 1; i <= 2000000; i++) {
            stringProducer.sendAsync(("My message:" + System.currentTimeMillis()).getBytes())
                    .thenAccept(msgId -> {});

            if (i % 10000 == 0) {
                long diff = System.currentTimeMillis() - curr;
                if (diff > 0)
                    tps = (10000 / diff)*1000;
                curr = System.currentTimeMillis();
                System.out.println(i + " done in " + diff + " millis tps:" + tps);
            }
        }
        CompletableFuture<Void> flfuture = stringProducer.flushAsync();
        flfuture.get();
        System.out.println("Final Flush completed");

        ProducerStats stats = stringProducer.getStats();
        long duration = System.currentTimeMillis() - start;
        System.out.println("\n\nAll done duration:" + duration);
        System.out.println("Stats TotalSent:" + stats.getTotalMsgsSent());
        System.out.println("Stats num Sent:" + stats.getNumMsgsSent());
        System.out.println("Stats TotalSentFailed:" + stats.getTotalSendFailed());
        System.out.println("Stats Max Latency:" + stats.getSendLatencyMillisMax());
        System.out.println("Stats SendMsgRate:" + stats.getSendMsgsRate());
        System.out.println("Stats Total Acks:" + stats.getTotalAcksReceived());

        CompletableFuture<Void> clfuture = stringProducer.closeAsync();
        clfuture.get();
        System.out.println("Close completed");

        client.close();
    }
}
