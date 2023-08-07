package com.in28minutes.microservices.camelmicroservicea.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component //comment this out to prevent this route being used and use another instead
public class MyFileRouter extends RouteBuilder {

    //check this out for more information:
    //https://camel.apache.org/components/3.21.x/languages/simple-language.html
    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .routeId("Files-Input-Route")
                .transform().body(String.class)
                .choice()
                    //.when(simple("${file:ext} ends with 'xml'"))
                    .when(simple("${file:ext} == 'xml'"))
                        .log("XML FILE")
                    .when(simple("${body} contains 'USD'"))
                        .log("Not an XML FILE BUT contains USD")
                    .otherwise()
                        .log("Not an XML FILE")
                .end()
                .log("${body}")
                .log("${messageHistory} ${headers.CamelFileAbsolute} ${file:absolute.path}")
                .to("file:files/output");
    }
}
