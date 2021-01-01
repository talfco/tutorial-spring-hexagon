package net.cloudburo.hexagon.demo.port.in.covid.staging;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/*
Another approach would be to use a Fauilure Bean
https://learning.oreilly.com/library/view/Camel+in+Action,+Second+Edition/9781617292934/c11.xhtml#c11-codeannotation-0010
 */
public class FailureProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String failure = "The message failed because " + e.getMessage();
        exchange.getIn().setHeader("failureMessage", failure);
    }
}
