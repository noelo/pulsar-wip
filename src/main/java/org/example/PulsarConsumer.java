package org.example;

import org.apache.pulsar.client.api.*;

public class PulsarConsumer {
    public PulsarConsumer() throws PulsarClientException {
        System.out.println("firing up consumer");

        PulsarClient client = PulsarClient.builder()
                .allowTlsInsecureConnection(Boolean.TRUE)
                .enableTlsHostnameVerification(Boolean.FALSE)
                .tlsTrustCertsFilePath("/home/noelo/dev/noc-pulsar-client/pulsar-proxy-chain.pem")
                .serviceUrl("pulsar+ssl://sslproxy-route-pulsar.apps.ocp.themadgrape.com:443")
                .enableTcpNoDelay(Boolean.TRUE)
                .build();

        Consumer<byte[]> stringConsumer = client.newConsumer()
                .subscriptionName("testConsumer")
                .topic("persistent://public/default/my-topic")
                .subscribe();

        long msg_count = 1;
        long start = System.currentTimeMillis();
        System.out.println("Starting consumer");
        while (msg_count < 2000000) {
            // Wait for a message
            Message msg = stringConsumer.receive();
            msg_count++;

            try {
                // Acknowledge the message so that it can be deleted by the message broker
                stringConsumer.acknowledge(msg);
                if (msg_count % 10000 == 0) System.out.println("Received " + msg_count);

            } catch (Exception e) {
                // Message failed to process, redeliver later
                stringConsumer.negativeAcknowledge(msg);
            }
        }
        stringConsumer.close();
        ConsumerStats stats = stringConsumer.getStats();
        long duration = System.currentTimeMillis() - start;
        System.out.println("All done duration:" + duration);
        System.out.println("Stats TotalReceived:" + stats.getTotalMsgsReceived());
        System.out.println("Stats MsgRate:" + stats.getRateMsgsReceived());
        System.out.println("Stats Total Acks:" + stats.getTotalAcksSent());
        client.close();


    }
}
