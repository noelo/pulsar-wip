package com.example.pulsar.functions;

import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.example.schema.CryptoPricesSchema;
import static org.example.schema.CryptoPricesSchema.ROUTING_KEY;

public class SymbolRouter implements Function<CryptoPricesSchema, Void> {
    public Void process(CryptoPricesSchema rawPrices, Context ctx) throws Exception {
        String routeKey = ctx.getCurrentRecord().getProperties().getOrDefault(ROUTING_KEY,"NONE");
        ctx.newOutputMessage(routeKey, JSONSchema.of(CryptoPricesSchema.class))
                    .properties(ctx.getCurrentRecord().getProperties())
                    .value(rawPrices).sendAsync();
        return null;
    }
}