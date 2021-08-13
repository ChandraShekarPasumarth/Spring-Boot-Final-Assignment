package com.luv2code.springboot.thymeleafdemo.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Configuration
@EnableJpaRepositories(basePackages={"com.luv2code.springboot.thymeleafdemo.dao"})
public class DemoDataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix="security.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
}
