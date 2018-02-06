package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.service.HelloService;
import spring.service.HelloServiceStub;

@Configuration
@Import(AppConfig.class)
public class AppConfigStub {
    @Bean
    // Note: method name must be exact same as the one in AppConfig
    public HelloService getHelloService() {
        // override the same name of the bean
        return new HelloServiceStub();
    }
}