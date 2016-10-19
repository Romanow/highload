package ru.romanow.restful;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * Created by romanow on 18.10.16
 */
@Configuration
@EnableJpaRepositories
public class DatabaseConfiguration {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private DataSource dataSource;

    @Bean
    @DependsOn("persistenceUnit")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setSchemas("highload");
        flyway.setOutOfOrder(true);
        return flyway;
    }
}