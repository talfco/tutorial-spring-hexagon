package net.cloudburo.hexagon.demo.port.in;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
class RestApiRouter extends RouteBuilder {

    @Value("${injector.api.path}")
    private String contextPath;
    @Value("${server.port}")
    private String serverPort;

    @Override
    public void configure() {

        /**
        Camel always needs a CamelContext instance – the core component where the incoming and outgoing messages are kept.
         In this simple example, DefaultCamelContext suffices as it just binds messages and routes into it,
         like the REST service that we are going to create.
         **/

        CamelContext context = new DefaultCamelContext();


        // Configure a Rest Service
        // http://localhost:8080/camel/api-doc
        restConfiguration().contextPath(contextPath) //
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true") // cross-site
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");
        /**
         The Rest DSL supports automatic binding json/xml contents to/from
         POJOs using Camels Data Format.
         By default the binding mode is off, meaning there is no automatic
         binding happening for incoming and outgoing messages.
         You may want to use binding if you develop POJOs that maps to
         your REST services request and response types.
         */

        rest("/api/")
                .description("Teste REST Service")
                .id("api-route")

                // Get verb
                .get("/bean")
                .to("direct:getService")

                // Post verb
                .post("/bean")
                .produces(MediaType.APPLICATION_JSON.getType())
                .consumes(MediaType.APPLICATION_JSON.getType())
                // .get("/hello/{place}")
                .bindingMode(RestBindingMode.auto)
                .type(MyBean.class)
                .enableCORS(true)
                // .outType(OutBean.class)
                .to("direct:remoteService");

        from("direct:getService").routeId("direct-get-route")
                .tracing()
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

        from("direct:remoteService").routeId("direct-route")
                .tracing()
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                // .transform().simple("blue ${in.body.name}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        MyBean bodyIn = (MyBean) exchange.getIn()
                                .getBody();

                        ExampleService.example(bodyIn);

                        exchange.getIn()
                                .setBody(bodyIn);
                    }
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
    }
}
