package com.in28minutes.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component //comment it out to prevent generating a lot of logs
public class KafkaSenderRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:files/kafka")
                .log("${body}")
                .to("kafka:myKafkaTopic");
    }
}
