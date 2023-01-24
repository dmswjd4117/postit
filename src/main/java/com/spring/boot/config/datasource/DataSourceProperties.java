package com.spring.boot.config.datasource;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource")
@Getter
@Setter
public class DataSourceProperties {

  private Map<String, DatasourceNode> slave = new HashMap<>();
  private DatasourceNode master;

  @ToString
  @Getter
  @Setter
  public static class DatasourceNode {

    private String name;
    private String url;
    private String username;
    private String password;
  }
}
