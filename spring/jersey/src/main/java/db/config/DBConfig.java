package db.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement  // needed, or transaction will not work
@PropertySource(value = "classpath:db_config.properties")  // must specify as classpath prefix to properly locate the file
public class DBConfig {
    public static final String JDBC_DRIVER = "jdbc.driver";
    public static final String JDBC_URI = "jdbc.uri";
    public static final String JDBC_USER = "jdbc.username";
    public static final String JDBC_PASSWD = "jdbc.passwd";
    static final String JDBC_UNIT_NAME = "jdbc.unitname";

    public static final String HIBERNATE_DDL = "hibernate.hbm2ddl.auto";
    public static final String HIBERNATE_DIALECT = "hibernate.dialect";

    @Inject
    private Environment env;

    @Bean
    public DataSource getDatasource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty(JDBC_DRIVER));
        ds.setUrl(env.getProperty(JDBC_URI));
        ds.setUsername(env.getProperty(JDBC_USER));
        ds.setPassword(env.getProperty(JDBC_PASSWD));
        return ds;
    }

    @Bean
    public HibernateJpaVendorAdapter getJpaVendorAdaptor() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public HibernateJpaDialect getJpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean(name = "entityManagerFactory")
    @Inject
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource datasource, HibernateJpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(datasource);
        em.setJpaVendorAdapter(adapter);
        em.setPersistenceUnitName(env.getProperty(JDBC_UNIT_NAME));
        em.setJpaProperties(additionalProperties());

        em.setPackagesToScan("db.model");  // w/o this, No persistence units parsed error will be raised
        return em;
    }

    @Bean
    @Inject
    public JpaTransactionManager getTransactionManager(EntityManagerFactory factory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(factory);
        return txManager;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty(HIBERNATE_DDL));
        properties.setProperty(AvailableSettings.DIALECT, env.getProperty(HIBERNATE_DIALECT));
        return properties;
    }
}
