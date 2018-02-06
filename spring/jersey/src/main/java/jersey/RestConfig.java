package jersey;

import jersey.config.AppBinder;
import jersey.filter.EndpointLoggingListener;
import jersey.filter.MyRequestFilter;
import jersey.filter.MyResponseFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class RestConfig extends ResourceConfig {
    public static String baseUrl = "/jersey";

    public RestConfig() {
        packages("jersey.rest", "jersey.service");

        register(MyRequestFilter.class);
        register(MyResponseFilter.class);
        register(new EndpointLoggingListener(""));

        // The scanned service(s) must be manually "binded" for CDI!
        register(new AppBinder());
    }
}
