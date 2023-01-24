package com.spring.boot.config;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.spring.boot.config.datasource.DataSourceProperties;
import com.spring.boot.config.datasource.DataSourceProperties.DatasourceNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@EnableConfigurationProperties(DataSourceProperties.class)
class DataSourcePropertiesTest {

  @Autowired
  private DataSourceProperties dataSourceProperties;

  @Test
  void bindDataSource_Master() {
    // given
    DatasourceNode expected = new DatasourceNode();

    System.out.println(dataSourceProperties.getSlave());
    System.out.println(dataSourceProperties.getMaster());
  }

}