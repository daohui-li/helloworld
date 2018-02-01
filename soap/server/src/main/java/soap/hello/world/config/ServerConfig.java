package soap.hello.world.config;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.hello_world_soap_http.Greeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import soap.hello.world.endpoints.GreeterImpl;
import soap.hello.world.interceptors.RequestInterceptor;
import soap.hello.world.logic.HelloBehavior;
import soap.hello.world.logic.PingMeLogic;
import soap.hello.world.logic.PingMeLogicImpl;

@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class ServerConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
        return new ServletRegistrationBean(new CXFServlet(), "/*");
    }

    @Bean
    public Greeter greeterImpl() {
        return new GreeterImpl();
    }

    @Bean
    public HelloBehavior greeterBehavior() {
        return new HelloBehavior();
    }

    @Bean
    public PingMeLogic pingMeLogic() {
        return new PingMeLogicImpl();
    }

    @Bean
    public EndpointImpl createEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, greeterImpl());
        endpoint.publish("/hello");

        Endpoint ep = endpoint.getServer().getEndpoint();
        ep.getInInterceptors().add(new LoggingInInterceptor());
        ep.getInInterceptors().add(requestInterceptor);
        ep.getInFaultInterceptors().add(new LoggingInInterceptor());
        ep.getOutFaultInterceptors().add(new LoggingOutInterceptor());
        ep.getOutInterceptors().add(new LoggingOutInterceptor());
        return endpoint;
    }
}
