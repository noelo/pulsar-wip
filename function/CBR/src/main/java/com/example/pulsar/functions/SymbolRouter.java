package com.example.pulsar.functions;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

public class SymbolRouter implements Function<String, Void> {

    public void process(CryptoPricesSchema prices, Context ctx) throws Exception {
        Class paymentType = pay.getMethodOfPayment().getType().getClass();
        Object payment = pay.getMethodOfPayment().getType();
        if (paymentType == ApplePay.class) {
            ctx.newOutputMessage(applePayTopic, AvroSchema.of(ApplePay.class))
                    .properties(ctx.getCurrentRecord().getProperties())
                    .value((ApplePay) payment).sendAsync();

        } else if (paymentType == CreditCard.class) {
            ctx.newOutputMessage(creditCardTopic, AvroSchema.of(CreditCard.class))
                    .properties(ctx.getCurrentRecord().getProperties())
                    .value((CreditCard) payment).sendAsync();

        } else if (paymentType == ElectronicCheck.class) {
            ctx.newOutputMessage(echeckTopic, AvroSchema.of(ElectronicCheck.class))
                    .properties(ctx.getCurrentRecord().getProperties())
                    .value((ElectronicCheck) payment).sendAsync();

        } else if (paymentType == PayPal.class) {
            ctx.newOutputMessage(paypalTopic, AvroSchema.of(PayPal.class))
                    .properties(ctx.getCurrentRecord().getProperties())
                    .value((PayPal) payment).sendAsync();

        } else {
            ctx.getCurrentRecord().fail();

        }
        return null;
    }
}