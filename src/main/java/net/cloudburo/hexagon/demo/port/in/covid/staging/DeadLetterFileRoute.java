package net.cloudburo.hexagon.demo.port.in.covid.staging;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.codec.digest.DigestUtils;
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
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${id}.${file:ext}"))
            .log("Unprocessed file record ${file:name} will be moved into folder "+covidStagingPortConfig.getDeadletter()+"/${header.useCase}")
            .toD("file://" + covidStagingPortConfig.getDeadletter()+"/${header.useCase}")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    exchange.getIn().setHeader("md5Key", DigestUtils.md5Hex((String)exchange.getIn().getBody()));
                }
            })
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}.txt"))
            .setBody(simple("Timestamp: ${date:now:yyyyMMddHHmmssSSS}\nId: ${id}\nFailure: ${header.failureMessage}"))
            .toD("file://" + covidStagingPortConfig.getDeadletter()+"/${header.useCase}")
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}.md5"))
            .setBody(simple("${header.md5key}"))
            .toD("file://" + covidStagingPortConfig.getDeadletter()+"/${header.useCase}");
    }
}
