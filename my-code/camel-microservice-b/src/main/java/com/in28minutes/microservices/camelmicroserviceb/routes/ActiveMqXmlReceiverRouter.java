package com.in28minutes.microservices.camelmicroserviceb.routes;

import com.in28minutes.microservices.camelmicroserviceb.CurrencyExchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component //comment it out to prevent generating a lot of logs
public class ActiveMqXmlReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-xml-queue")
                .unmarshal().jacksonXml(CurrencyExchange.class)
                .to("log:received-message-from-active-mq");
    }
}