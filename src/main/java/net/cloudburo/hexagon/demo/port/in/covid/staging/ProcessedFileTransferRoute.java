package net.cloudburo.hexagon.demo.port.in.covid.staging;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessedFileTransferRoute extends RouteBuilder {

    @Autowired
    private CovidStagingPortConfig covidStagingPortConfig;

    @Override
    public void configure() throws Exception {
        from("direct://processedFileTransfer").routeId("processed-file-transfer-route")
            .log("Processed file ${file:name} will be moved into folder "+covidStagingPortConfig.getTarget()+"/${header.useCase}")
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}-${exchangeId}.${file:ext}"))
            .toD("file://" + covidStagingPortConfig.getTarget()+"/${header.useCase}")
            // Let's calculate the MD5 Idempotent key of the content
            .process(new MD5FileContentProcessor())
            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}.md5"))
            .log("MD5 Key: ${header.md5key}")
            .setBody(simple("${header.md5key}"))
            .toD("file://" + covidStagingPortConfig.getTarget()+"/${header.useCase}");
    }
}
