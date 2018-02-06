# Spring-Jersey Integration

## Overview

The main purpose of this web project is to demonstrates the usage of the integration of Spring framework and Jersey 
framework, which are two popular frameworks used in web server.

[Jersey framework](https://jersey.github.io/) was maintained by Oracle. 
By implementing JAX-RS API, which specifies *RESTful* operations, the 
framework provides support for RESTful web service.

[Spring framework](https://docs.spring.io/spring/docs/3.0.x/spring-framework-reference/html/overview.html)
is a framework that provides comprehensive support for developing Java
applications. In this project, Spring's core and web components are used 
for bootstrap of the web service and for providing support of Dependency Injection.

This project also uses [Hibernate ORM](http://hibernate.org/orm/) to interface relational
database.

## High level description

### Environment

#### Build environment

This project is built by Maven with pom.xml with compilation information
retrieved from its parent (see pom.xml at the top level). 
The project specifies the external dependencies. Notably:  

| Framework | Version |
|: ------ :|: --- |
| Spring | 4.3.9.RELEASE |
| Jersey | 2.26 |
| Hibernate | 5.2.9.Final |

The reasons of depdencies are specified whenever it deems necessary.
 
#### Test environment

tomcat7 is used to launch an embedded tomcat with port set to 8168 (in pom.xml).

_JerseyTest_ is used in testing _HelloResource_ interace. The services behavior can be altered by
creating *identical* method name in _AppConfigStub_. 

*TODO*: use in memory database, such as H2, to test database related code. At the moment, 
@Ignore is used to instruct maven to ignore database related test cases in the test phase.

#### Runtime envionment

_web.xml_ is not truly necessary. However, the file is used to conveniently specify welcome page, index.html, and 404 page,
404.jsp, when the return code is 404. 

_db\_config.properties_ defines database related information, and the file is referenced in _DBConfig_.

_logback.xml_ specifies the configuration of logging facility. 

MySQL must be installed and running with the account information consisting with that specified in 
_db\_config.properties_. The database, _pring\_hibernate\_dev_, must be created.

### Bootstrap

_ApplicationInitializer_, which implements Spring's _WebApplicationInitializer_ interface,
defines the entry point of the bootstrap of the web service. The class performs the following
during the bootstrap:
1. creates a container, which is known as Web Application Context, and registers locations
of packages contains Java Beans (both Spring's and Hibernate's Beans)
2. creates a listener that monitors the container's state; based on the container's
state, the listener creates/destroies Java Beans 
3. creates a jersey servlet, specifies the request pattern the servlet handles, and
specifies the Jersey entry point.

_RestConfig_, which extends Jersey's _ResourceConfig_ class, is the entry point of Jersey-related
code. The class performs following during the object initializaion:
1. registers packages so that Jersey will scan the classes in those packages
2. registers classes for filters and services

_EndpointLoggingListener_, which is registered with Spring framework in _RestConfig_, handles Application events. 
When the initilization complete event receives, it logs the the RESTful API endpoints.
  
### RESTful requests

* _HelloResource_ defines two GET operations: _${base_url}/jersey/hello\[/<a_name>\]; 
it also tests the CDI by injecting _HelloService_, a Spring bean, and _J2EEHello_, a J2EE bean, into _HelloResource_;
  *TODO*: _J2EEHelloImpl_ has to be manually binded to _J2EEHello_, in _AppBinder_; need to find a
  way to automatically bind the implementation class to the correspoonding interfaces.
  [Jersey-HK service locator](https://javaee.github.io/hk2/getting-started.html) appears to be the 
  solution of the problem. 
* _EmployeeResource_ defines CRUD operations on Employee objects: ${base_url}/jersey/employees\[/id\]; 
the operations perform database operations mediated by _Hibernate ORM_ 
* Requests and Responses of these two resources are intercepted by _MyRequestFilter_ and _MyReponseFilter_, respectively.
Both filters are registered to the Jersey framework at the bootstrap stage.
* RuntimeException, if it occurs, will be intercepted and handled by _RuntimeExceptionMapper_. The latter
registers to the Jersey framework in _RestConfig_.
