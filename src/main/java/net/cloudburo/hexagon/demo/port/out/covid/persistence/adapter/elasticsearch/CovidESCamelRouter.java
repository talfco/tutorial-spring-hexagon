package net.cloudburo.hexagon.demo.port.out.covid.persistence.adapter.elasticsearch;

import net.cloudburo.hexagon.demo.domain.covid.CovidCase;
import net.cloudburo.hexagon.demo.domain.covid.Header;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidPersistencyPortConfig;
import net.cloudburo.hexagon.demo.port.out.covid.persistence.CovidSerializer;
import org.apache.avro.SchemaNormalization;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CovidESCamelRouter extends RouteBuilder {

    // TODO: Implement Router
    @Autowired
    private CamelContext camelContext;
    @Autowired
    private CovidPersistencyPortConfig config;
    @Autowired
    private ESPersistencyConfig esPersistencyConfig;


    public void persistDailyCovidRecord(CovidCase record) throws Exception {
        String id = record.getCountryTerritoryCode()+"-"+record.getReportingYear()+"-"+record.getReportingMonth()+"-"+ record.getReportingDay();

        // We inject the Avro fingerprint into the JSON Document, in a sense
        // we seal the document with its specific Schema, this will allow us
        // during retrieval of the document to detect the right schema which
        // can interpret the document (provided by the Schema registry)
        Header header = Header.newBuilder()
                .setAvroFingerprint(SchemaNormalization.parsingFingerprint64(CovidCase.getClassSchema()))
                .setLastUpdateTimestamp(java.lang.System.currentTimeMillis())
                .build();

        CovidCase updRecord = CovidCase.newBuilder(record)
                .setHeader(header)
                .build();

        String jsonDoc = CovidSerializer.serializeJSON(updRecord);
        ProducerTemplate template = camelContext.createProducerTemplate();
        template.sendBody("direct:start", jsonDoc);
    }


    @Override
    public void configure() throws Exception {
        /**  TODO: Maven Dependcy problems, doesn't work
        ElasticsearchComponent elasticsearchComponent = new ElasticsearchComponent();
        elasticsearchComponent.setHostAddresses(esPersistencyConfig.getHostaddress());
        camelContext.addComponent("elasticsearch-rest", elasticsearchComponent);
        from("direct:start")
                .to("elasticsearch-rest://elasticsearch?operation=Index&indexName="+config.getIndex()+"&indexType=base");
        **/
    }
}
