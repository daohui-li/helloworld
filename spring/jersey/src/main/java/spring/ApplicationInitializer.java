package spring;

import db.config.DBConfig;
import db.config.DaoConfig;
import jersey.RestConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import spring.config.AppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class, DBConfig.class, DaoConfig.class);

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.setInitParameter(
                ContextLoader.CONFIG_LOCATION_PARAM, "NONE");

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("JerseyDispatcher", new ServletContainer());
        dispatcher.setInitParameter("javax.ws.rs.Application", RestConfig.class.getCanonicalName());
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(RestConfig.baseUrl + "/*");
    }
}
