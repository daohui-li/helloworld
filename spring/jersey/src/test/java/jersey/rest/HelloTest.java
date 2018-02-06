package jersey.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import jersey.RestConfig;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.config.AppConfigStub;
import spring.service.HelloServiceStub;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloTest extends JerseyTest {
    @Override
    protected Application configure() {
        set(TestProperties.LOG_TRAFFIC, true);
        set(TestProperties.DUMP_ENTITY, true);

        ResourceConfig resourceConfig = createResourceConfig();
        resourceConfig.property("contextConfig", setupAppContext());

        return resourceConfig;
    }

    private AnnotationConfigApplicationContext setupAppContext() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.register(AppConfigStub.class);
        appContext.refresh();
        return appContext;
    }

    private ResourceConfig createResourceConfig() {
        ResourceConfig rc = new RestConfig();
        // TODO: register filters?
        return rc;
    }

    @Test
    public void testHello() throws IOException {
        final Response response = target("hello").request().get(Response.class);
        verifyResponse(response, "world");
    }

    private void verifyResponse(Response response, String name) throws IOException {
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        String expectedString = new HelloServiceStub().hello(name);
        assertThat(inputStreamToString((InputStream) response.getEntity()), is(expectedString));

    }

    @Test
    public void testHello2() throws IOException {
        final Response response = target("hello/abc").request().get(Response.class);
        verifyResponse(response, "abc");
    }

    @Test(expected = NotFoundException.class)
    public void test404Exception() {
        final ClientResponse response = target("noresource").request().get(ClientResponse.class);
    }

    private String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader((new InputStreamReader(is)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
