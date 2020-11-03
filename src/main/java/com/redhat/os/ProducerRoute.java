package com.redhat.os;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.amqp.AMQPComponent;

@ApplicationScoped
public class ProducerRoute extends RouteBuilder {

    @Inject
    Counter counter;
    
    @Override
    public void configure() throws Exception {

        // AMQPComponent amqp = getContext().getComponent("amqp", AMQPComponent.class);
        
        from("timer:foo?period=1000")
            .setBody(exchange -> "Incremented the counter: " + counter.increment())
            .to("log:message-payload")
            .to("amqp:topic:counterTopic?clientId=amqp-quarkus-producer");
                      
    }
    
}

