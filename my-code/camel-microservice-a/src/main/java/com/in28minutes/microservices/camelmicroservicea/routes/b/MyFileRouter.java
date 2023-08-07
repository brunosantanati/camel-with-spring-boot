package com.in28minutes.microservices.camelmicroservicea.routes.b;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component //comment this out to prevent this route being used and use another instead
public class MyFileRouter extends RouteBuilder {

    @Autowired
    private DeciderBean deciderBean;

    //check this out for more information:
    //https://camel.apache.org/components/3.21.x/languages/simple-language.html
    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .pipeline() //default -> Pipeline Pattern
                .routeId("Files-Input-Route")
                .transform().body(String.class)
                .choice() // Content Based Routing Pattern
                    //.when(simple("${file:ext} ends with 'xml'"))
                    .when(simple("${file:ext} == 'xml'"))
                        .log("XML FILE")
                    //.when(simple("${body} contains 'USD'"))
                    .when(method(deciderBean))
                        .log("Not an XML FILE BUT contains USD")
                    .otherwise()
                        .log("Not an XML FILE")
                .end()
                .log("${body}")
                //.to("direct://log-file-values")
                .to("file:files/output");

        from("direct:log-file-values")
                .log("${messageHistory} ${headers.CamelFileAbsolute} ${file:absolute.path}")
                .log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
                .log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
                .log("${file:size} ${file:modified}")
                .log("${routeId} ${camelId} ${body}");
    }
}

@Component
class DeciderBean {

    Logger logger = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisConditionMet(@Body String body,
                                      @Headers Map<String, String> headers,
                                      @Header("CamelFileAbsolutePath") String header,
                                      @ExchangeProperties Map<String, String> exchangeProperties) {
        logger.info("DeciderBean {} {} {} {}", body, header, headers, exchangeProperties);
        return true;
    }

}
