package com.data.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Value;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConfigurationProperties("spring.app-datasource")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "appEntityManagerFactory",
        transactionManagerRef = "appTransactionManager",
        basePackages = {"com.data.repository"}
)
@Value
public class DataSourceConfig extends HikariConfig {

    public static final String PERSISTENCE_UNIT_NAME = "app";
    public static final String ENTITY_PACKAGE = "com.data.entity";
    private String databasePlatform;
    private String defaultSchema;
    private boolean showSql;
    private boolean validateSchema;

    @Bean
    @Primary
    @Qualifier("appDataSource")
    public DataSource appDataSource() {
        return new HikariDataSource(this);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(@Qualifier("appDataSource") final DataSource appDataSource) {
        final Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", databasePlatform);
        jpaProperties.put("hibernate.default_schema", defaultSchema);
        if (showSql) {
            jpaProperties.put("hibernate.show-sql", "true");
        }
        if (validateSchema) {
            jpaProperties.put("hibernate.hbm2ddl.auto", "validate");
        }
        final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(appDataSource);
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        localContainerEntityManagerFactoryBean.setPackagesToScan(ENTITY_PACKAGE);
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager appTransactionManager(EntityManagerFactory appEntityManagerFactory) {
        return new JpaTransactionManager(appEntityManagerFactory);
    }

}
