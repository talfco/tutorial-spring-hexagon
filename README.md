# README

Leveraging the excellent work from:

* https://gitlab.com/vkrava4/avro-converter/
* https://github.com/thombergs/buckpal

# Guidance

* The Avro Schema can be found in `resources/avro/userDomainModel.avsc`
* The generated classes will be in `net.cloudburo.hexagon.demo.domain`
* Every JSON document get the fingerprint injected of the `userDomainModel.avsc` (i.e. it's version number)


# Additional Information

## Spring HATEOAS
The Spring HATEOAS project is a library of APIs that we can use to easily create REST representations that follow the 
principle of HATEOAS _Hypertext as the Engine of Application State_.

Generally speaking, the principle implies that the API should guide the client through the application by returning 
relevant information about the next potential steps, along with each response.

In a Spring HATEOAS project, we don't need to either look up the Servlet context nor concatenate the path variable to 
the base URI.

Instead, Spring HATEOAS offers three abstractions for creating the URI – `RepresentationModel`, `Link`, and 
`WebMvcLinkBuilder`. 

We can use these to create the metadata and associate it to the resource representation.

Large Data Files for Testing

* https://data.world/acled/71d852e4-e41e-4320-a770-9fc2bb87fb64

## Data Sets

* Covid-19 Coronavirus EU Open Data Portal: Data on the geographic distribution of COVID-19 cases worldwide
  * [Data Set URL](https://data.europa.eu/euodp/en/data/dataset/covid-19-coronavirus-data/resource/55e8f966-d5c8-438e-85bc-c7a5a26f4863) or
    [Data Set URL 2](https://www.ecdc.europa.eu/en/publications-data/download-todays-data-geographic-distribution-covid-19-cases-worldwide)
 released under [Creative Commons 4.0](https://creativecommons.org/licenses/by/4.0/)
* COVID-19 Finance Sector Related Policy Responses by the World Bank.
    *  [Data Set URL](https://www.ecdc.europa.eu/en/publications-data/download-todays-data-geographic-distribution-covid-19-cases-worldwide) 
       released under [Creative Commons 4.0](https://creativecommons.org/licenses/by/4.0/)
  

References: 

*   https://www.baeldung.com/spring-hateoas-tutorial
*   https://www.baeldung.com/apache-camel-spring-boot
*   https://www.baeldung.com/apache-camel-intro
*   https://www.baeldung.com/configuration-properties-in-spring-boot
*   https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html#creating-http-request-files

* https://www.elastic.co/webinars/moving-from-on-prem-to-managed-services-with-elastic-on-azure?blade=kibanafeed

* https://www.elastic.co/guide/en/elasticsearch/reference/current/removal-of-types.html#:~:text=Since%20the%20first%20release%20of,type%20and%20a%20tweet%20type.
