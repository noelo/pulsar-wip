package org.example;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.shade.org.apache.avro.SchemaBuilder;

import java.util.concurrent.TimeUnit;

public class pulsarclient {
    public pulsarclient() throws PulsarClientException {
        System.out.println("firing up client");
        PulsarClient client = PulsarClient.builder()
                .allowTlsInsecureConnection(Boolean.TRUE)
                .enableTlsHostnameVerification(Boolean.FALSE)
//                .useKeyStoreTls(Boolean.TRUE)
//                .tlsTrustStorePassword("changeit")
//                .tlsTrustStorePath("/home/noelo/dev/noc-pulsar-client/cacerts")
//                .tlsTrustStoreType("PEM")
                .tlsTrustCertsFilePath("/home/noelo/dev/noc-pulsar-client/pulsar-proxy-chain.pem")
//                .proxyServiceUrl("https://proxy-route-pulsar.apps.ocp.themadgrape.com:443", ProxyProtocol.SNI)
//                .serviceUrl("pulsar+ssl://pulsar-broker:6651")
//                .serviceUrl("pulsar+ssl://proxy-route-pulsar.apps.ocp.themadgrape.com:443")
                .serviceUrl("pulsar+ssl://sslproxy-route-pulsar.apps.ocp.themadgrape.com:443")
                .operationTimeout(2000, TimeUnit.MILLISECONDS)
                .build();
        System.out.println("After client init " + client.isClosed());
        Producer<String> stringProducer = client.newProducer(Schema.STRING)
                .topic("my-topic")
                .create();
        long start = System.currentTimeMillis();
        long curr = System.currentTimeMillis();
        for(int i=0; i<100000;i++){
            stringProducer.send("My message:" + i);
            if (i % 1000 == 0){
                long diff = (System.currentTimeMillis() - curr)/1000;
                curr = System.currentTimeMillis();
                System.out.println("1000 done @" + curr + " tps :"+diff);
            }

        }
        long duration = System.currentTimeMillis()-start;
        System.out.println("All done duration:"+duration);
        stringProducer.close();
        client.close();


    }
}
