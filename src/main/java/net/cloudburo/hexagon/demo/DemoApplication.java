package net.cloudburo.hexagon.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Is a convencience method and add
 * @Configuration: Tags the class as a source of bean definitions for the application context.
 * @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and
 * various property settings.
 * For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and
 * activates key behaviors, such as setting up a DispatcherServlet.
 * @ComponentScan: Tells Spring to look for other components, configurations, and services in the
 * net/cloudburo/hexagon/demo package, letting it find the controllers.
 */
@SpringBootApplication
public class DemoApplication {


    /**
     * The main() method uses Spring Boot’s SpringApplication.run() method to launch an application. Did you notice
     * that there was not a single line of XML? There is no web.xml file, either. This web application is 100% pure
     * Java and you did not have to deal with configuring any plumbing or infrastructure.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
