package org.example.pulsar.functions;

import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.example.schema.CryptoPricesSchema;

import static org.example.schema.CryptoPricesSchema.ROUTING_KEY;

public class SymbolRouter implements Function<CryptoPricesSchema, Void> {

    @Override
    public Void process(CryptoPricesSchema rawPrices, Context ctx) throws Exception {
        String routeKey = ctx.getCurrentRecord().getProperties().getOrDefault(ROUTING_KEY,"NONE");
//        ctx.getCurrentRecord().getProperties().forEach((k,v) -> {
//            ctx.getLogger().info("K:V {} {}",k,v);
//        });
//        ctx.getLogger().info("Message {} ==> {}",ctx.getCurrentRecord().getMessage().get().getMessageId(),rawPrices);

        ctx.newOutputMessage(routeKey, JSONSchema.of(CryptoPricesSchema.class))
                    .properties(ctx.getCurrentRecord().getProperties())
                    .value(rawPrices).sendAsync();
        ctx.recordMetric(routeKey+"-count", 1);
        return null;
    }
}