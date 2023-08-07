package com.in28minutes.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CsvSplitterReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:split-queue")
                .to("log:received-message-from-active-mq");
    }
}