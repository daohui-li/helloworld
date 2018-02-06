package db.config;

import db.dao.EmployeeDao;
import db.dao.EmployeeDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoConfig {
    @Bean
    public EmployeeDao employeeDao() {
        return new EmployeeDaoImpl();
    }
}
