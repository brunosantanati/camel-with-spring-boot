package com.in28minutes.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EipPatternsRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Multicast Pattern
        /*from("timer:multicast?period=10000")
                .multicast()
                .to("log:something1", "log:something2", "log:something3");*/

        // Splitter Pattern
        from("file:files/csv")
                .unmarshal().csv()
                .split(body())
                .to("log:split-files");
    }
}
