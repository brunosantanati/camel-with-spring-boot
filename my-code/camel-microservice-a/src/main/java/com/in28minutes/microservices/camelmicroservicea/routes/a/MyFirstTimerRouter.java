package com.in28minutes.microservices.camelmicroservicea.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
                //.transform().constant("My Constant Message")
                //.transform().constant("Time now is " + LocalDateTime.now())
                //.bean("getCurrentTimeBean")
                //.bean(getCurrentTimeBean, "getCurrentTime") //we can specify the method, but it's not necessary when the class has only one method
                .bean(getCurrentTimeBean)
                .to("log:first-timer");
    }

}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
}
