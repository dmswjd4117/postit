package com.spring.boot.config.datasource;

import com.spring.boot.config.datasource.DataSourceProperties.DatasourceNode;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
@Configuration
@Profile("!test")
public class DataSourceConfig {
  private final DataSourceProperties dataSourceProperties;
  private final JpaProperties jpaProperties;

  public DataSourceConfig(DataSourceProperties dataSourceProperties, JpaProperties jpaProperties) {
    this.dataSourceProperties = dataSourceProperties;
    this.jpaProperties = jpaProperties;
  }

  private DataSource createDataSource(DatasourceNode datasourceNode) {
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .url(datasourceNode.getUrl())
        .driverClassName("com.mysql.cj.jdbc.Driver")
        .username(datasourceNode.getUsername())
        .password(datasourceNode.getPassword())
        .build();
  }

  @Bean
  public DataSource routingDataSource() {

    // DataSource 설정
    Map<Object, Object> dataSources = new HashMap<>();
    DataSource master = createDataSource(dataSourceProperties.getMaster());
    dataSources.put("master", master);
    dataSourceProperties.getSlave().forEach((key, value) ->
        dataSources.put(key, createDataSource(value))
    );


    ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();
    replicationRoutingDataSource.setDefaultTargetDataSource(dataSources.get("master"));
    replicationRoutingDataSource.setTargetDataSources(dataSources);

    return replicationRoutingDataSource;
  }

  @Bean
  public DataSource dataSource() {
    return new LazyConnectionDataSourceProxy(routingDataSource());
  }



  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    EntityManagerFactoryBuilder entityManagerFactoryBuilder = createEntityManagerFactoryBuilder(jpaProperties);
    return entityManagerFactoryBuilder.dataSource(dataSource())
        .packages("com.spring.boot")
        .build();
  }


  private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(JpaProperties jpaProperties) {
    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    return new EntityManagerFactoryBuilder(vendorAdapter, jpaProperties.getProperties(), null);
  }


  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager tm = new JpaTransactionManager();
    tm.setEntityManagerFactory(entityManagerFactory);
    return tm;
  }

}
