package com.in28minutes.microservices.camelmicroservicea.routes.patterns;

import com.in28minutes.microservices.camelmicroservicea.CurrencyExchange;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EipPatternsRouter extends RouteBuilder {

    @Autowired
    SplitterComponent splitter;

    @Autowired
    DynamicRouterBean dynamicRouterBean;

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
        /*from("file:files/csv")
                .convertBodyTo(String.class)
                .split(method(splitter))
                .log("${body}")
                .to("activemq:split-queue");*/

        // Aggregation Enterprise Integration Pattern
        /*from("file:files/aggregate-json")
                .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
                .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
                .completionSize(3)
                //.completionTimeout(HIGHEST)
                .to("log:aggregate-json");*/

        // Routing Slip Enterprise Integration Pattern
        /*String routingSlip = "direct:endpoint1, direct:endpoint3";

        from("timer:routingSlip?period=10000")
                .transform().constant("My message is hardcoded")
                        .routingSlip(simple(routingSlip));

        from("direct:endpoint1")
                .to("log:direct-endpoint1");

        from("direct:endpoint2")
                .to("log:direct-endpoint2");

        from("direct:endpoint3")
                .to("log:direct-endpoint3");*/

        // Dynamic Routing Enterprise Integration Pattern
        from("timer:dynamicRouting?period={{timePeriod}}") // Use property from application.properties
                .transform().constant("My message is hardcoded")
                .dynamicRouter(method(dynamicRouterBean));

        from("direct:endpoint1")
                .to("{{endpoint-for-logging}}"); // Use property from application.properties

        from("direct:endpoint2")
                .to("log:direct-endpoint2");

        from("direct:endpoint3")
                .to("log:direct-endpoint3");
    }
}

@Component
class SplitterComponent {
    public List<String> splitInput(String body) {
        //here we would process the body and split it, but let's just return a random list
        return List.of("ABC", "DEF", "GHI");
    }
}

@Component
class DynamicRouterBean {
    Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);

    int invocations;

    public String decideTheNextEndpoint(
            @ExchangeProperties Map<String, String> properties,
            @Headers Map<String, String> headers,
            @Body String body
    ) {
        logger.info("{} {} {}", properties, headers, body);

        invocations++;

        if(invocations % 3 == 0)
            return "direct:endpoint1";

        if(invocations % 3 == 1)
            return "direct:endpoint2, direct:endpoint3";

        return null;
    }
}
