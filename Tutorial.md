# Tutorial

## Camel

### In-memory messaging
From: https://tomd.xyz/camel-direct-vm-seda/

To avoid having to repeat the same route code, Camel has features built-in which allow routes to have multiple inputs, 
by using a range of joining components to glue these routes together.

Camel glues endpoints together using the components `Direct`, `VM` and `SEDA`.

These components join your Camel routes together in different ways. They are collectively known as Camel’s in-memory 
messaging components, because they allow messages to be passed between routes, while the message stays in memory at all times.

#### Direct
Apache Camel’s Direct component is a way of joining routes together. When it is used as a start component in a route, 
it can be invoked from other routes, as a way of triggering the route. 

_As Direct is a synchronous component, execution continues in the same thread._

Drawbacks: Direct endpoints can only be accessed by other routes that are running in the same CamelContext and in the
same JVM. This means that you cannot access a Direct endpoint from another CamelContext.

#### Direct-VM
Direct-VM is a component that allows you to synchronously call another endpoint in the same JVM, even if it’s in a 
different CamelContext.

When used as a start component, Direct-VM exposes that route as an endpoint which can be invoked synchronously from 
another route.

The difference with the Direct-VM component is that direct-vm endpoints can be seen from other Camel Contexts, as 
long as they share the same Java Virtual Machine (JVM).

#### SEDA Component
Camel’s SEDA component allows you to join routes together using a simple queue. In a Camel route, when a message is 
sent to a SEDA endpoint, it is stored in a basic in-memory queue, and control is returned back to the calling route 
immediately.

Then, independently, a SEDA consumer picks up the message from the queue, and begins processing it.

Out of the box, SEDA creates a pool of threads to process incoming messages, meaning that several messages can be 
processed at once, making it potentially more performant.

In this way, SEDA can be thought of as a simple replacement for JMS queues. It provides queue-like functionality, 
but without the overhead of running an external message broker like ActiveMQ.

Remember that Camel publishes messages to a SEDA endpoint asynchronously.

You can only access SEDA endpoints that are located in the same CamelContext

#### VM Component
In a similar way to how Direct and Direct-VM are related, VM is a similar component to SEDA.

When used as a start component, SEDA allows a route to be invoked asynchronously from another route.

However the difference between SEDA and VM is that the VM component allows endpoints to be accessed from different 
Camel Contexts, as long as they are running in the same JVM.

### Drawback of SEDA and VM

The biggest drawback of using in-memory messaging like SEDA and VM is that if the application crashes, there’s a big 
chance you’ll lose all your messages.