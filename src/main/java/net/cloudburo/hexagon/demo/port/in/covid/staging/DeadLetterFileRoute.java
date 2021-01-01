package net.cloudburo.hexagon.demo.port.in.covid.staging;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterFileRoute  extends RouteBuilder {

    @Autowired
    CovidStagingPortConfig covidStagingPortConfig;

    @Override
    public void configure() throws Exception {
        from("direct://dlStagingFileRoute").routeId("deadletter-staging-file-route")
            .log("Dead Letter Queue Event ${id}: ${header.failureMessage} - ${body}")
            .setHeader(Exchange.FILE_NAME, simple("${header.useCase}-${file:name.noext}-${id}.${file:ext}"))
            .log("Unprocessed file ${file:name} will be moved into folder "+covidStagingPortConfig.getDeadletter() +"/"+simple("${header.useCase}"))
            .to("file://" + covidStagingPortConfig.getDeadletter())
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}.txt"))
            .setBody(simple("Timestamp: ${date:now:yyyyMMddHHmmssSSS}\nId: ${id}\nFailure: ${header.failureMessage}"))
            .to("file://" + covidStagingPortConfig.getDeadletter());
    }
}
