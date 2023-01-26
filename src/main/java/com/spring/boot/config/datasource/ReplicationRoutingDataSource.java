package com.spring.boot.config.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReplicationRoutingDataSource.class);
  private static final String MASTER_NAME = "master";
  private static final String SLAVE_NAME = "slave";

  private List<String> slaveNames = new ArrayList<>();
  private int counter = 0;

  @Override
  public void setTargetDataSources(Map<Object, Object> targetDataSources) {
    super.setTargetDataSources(targetDataSources);

    slaveNames = targetDataSources.keySet().stream()
        .map(Object::toString)
        .filter(string -> string.startsWith(SLAVE_NAME))
        .collect(Collectors.toList());
  }

  @Override
  protected String determineCurrentLookupKey() {
    boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

    if (isReadOnly) {
      String slaveName = getNextSlaveName();

      LOGGER.info("Connected Slave DB Name : {}", slaveName);

      return slaveName;
    }

    LOGGER.info("Connected Master DB Name : {}", MASTER_NAME);
    return MASTER_NAME;
  }

  private String getNextSlaveName() {
    String nextSlave = slaveNames.get(counter);
    counter = (counter + 1) % slaveNames.size();
    return nextSlave;
  }
}