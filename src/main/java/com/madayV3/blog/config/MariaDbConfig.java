package com.madayV3.blog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MariaDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "maria.datasource")
    public DataSource mariaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate mariaJdbcTemplate(@Qualifier("mariaDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
