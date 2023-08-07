package com.in28minutes.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EipPatternsRouter extends RouteBuilder {

    @Autowired
    SplitterComponent splitter;
    @Override
    public void configure() throws Exception {

        // Multicast Pattern
        /*from("timer:multicast?period=10000")
                .multicast()
                .to("log:something1", "log:something2", "log:something3");*/

        // Splitter Pattern - example 1
        /*from("file:files/csv")
                .unmarshal().csv()
                .split(body())
                //.to("log:split-files");
                .transform().body(String.class) //I needed to put this line as a way around the RuntimeCamelException
                .log("${body}")
                .to("activemq:split-queue");*/

        // Splitter Pattern - example 2
        /*from("file:files/csv")
                .convertBodyTo(String.class)
                .split(body(), ",") //split by comma
                .log("${body}")
                .to("activemq:split-queue");*/

        // Splitter Pattern - example 3 using a custom splitter
        from("file:files/csv")
                .convertBodyTo(String.class)
                .split(method(splitter))
                .log("${body}")
                .to("activemq:split-queue");
    }
}

@Component
class SplitterComponent {
    public List<String> splitInput(String body) {
        //here we would process the body and split it, but let's just return a random list
        return List.of("ABC", "DEF", "GHI");
    }
}
