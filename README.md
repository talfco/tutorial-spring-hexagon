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

References: 

*   https://www.baeldung.com/spring-hateoas-tutorial
*   https://www.baeldung.com/apache-camel-spring-boot
*   https://www.baeldung.com/apache-camel-intro
*   https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html#creating-http-request-files


