package jersey.rest;

import jersey.service.J2EEHello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("hello")
public class HelloResource {
    private static Logger LOGGER = LoggerFactory.getLogger(HelloResource.class);

    @Inject
    private HelloService service;

    @Inject
    private J2EEHello jerseyService;

    @GET
    public String hello() {
        LOGGER.info("hello() called." + jerseyService.sayHello("world"));
        return service.hello("world");
    }

    @GET
    @Path("{name}")
    public String hello(@PathParam("name") String name) {
        return service.hello(name);
    }
}
