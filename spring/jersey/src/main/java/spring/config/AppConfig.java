package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.service.HelloService;
import spring.service.impl.HelloServiceImpl;

@Configuration
public class AppConfig {
    @Bean
    public HelloService getHelloService() {
        return new HelloServiceImpl();
    }
}
