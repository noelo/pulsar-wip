package org.example.pulsar.functions;

import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.example.schema.FixedIncomeReturnMacroSchema;

import static org.example.schema.FixedIncomeReturnMacroSchema.ROUTING_KEY;


public class SymbolRouter implements Function<FixedIncomeReturnMacroSchema, Void> {

    @Override
    public Void process(FixedIncomeReturnMacroSchema fixedData, Context ctx) throws Exception {
        String routeKey = ctx.getCurrentRecord().getProperties().getOrDefault(ROUTING_KEY,"1900");
        ctx.newOutputMessage(routeKey, JSONSchema.of(FixedIncomeReturnMacroSchema.class))
                    .properties(ctx.getCurrentRecord().getProperties())
                    .value(fixedData).sendAsync();
        ctx.recordMetric(routeKey+"-count", 1);
        return null;
    }
}