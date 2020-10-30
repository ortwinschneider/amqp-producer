package com.redhat.os;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class ProducerRoute extends RouteBuilder {

    @Inject
    Counter counter;

    @Override
    public void configure() throws Exception {
        
        from("timer:foo?period=1000")
            .setBody(exchange -> "Incremented the counter: " + counter.increment())
            .to("amqp:topic:counterTopic?clientId=amqp-quarkus-producer");
                

        from("amqp:topic:counterTopic")  
        .to("log:example");      
    }
    
}
