package soap.hello.world.config;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import soap.hello.world.listener.CustomLoggerListener;

@Configuration
public class SecurityConfig {
    @Value("${ssl.server.port}")
    private String sslServerPort;

    @Value("${basic.auth.list}")
    private String userPasswordList;

    @Bean
    @Autowired
    @Qualifier("sslConnector")
    public EmbeddedServletContainerFactory servletContainer(Connector sslConnector ) {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(sslConnector);
        return tomcat;
    }

    @Bean
    @Resource(name = "sslConnector")
    public Connector createSSLConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(Integer.parseInt(sslServerPort));
        connector.setScheme("https");
        connector.setSecure(true);
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File keystore = new ClassPathResource("keystore.jks").getFile();
            protocol.setSSLEnabled(true);
            protocol.setKeystoreFile(keystore.getAbsolutePath());
            protocol.setKeystorePass("secret1");
            protocol.setKeyAlias("soap.server");
        } catch (IOException e) {
            // TODO
        }

        return connector;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = auth
                .inMemoryAuthentication();
        String[] tokenss = userPasswordList.split(";");
        for (String token : tokenss) {
            String[] pair = token.split("[,:]");
            if (pair.length == 2) {
                configurer.withUser(pair[0]).password(pair[1]).roles("USER");
            }
        }
        configurer
                .withUser("user").password("password").roles("USER");
    }

    @Bean
    public CustomLoggerListener getCustomerLoggerListener() {
        return new CustomLoggerListener();
    }
}
