package com.spring.boot.util;

import com.google.common.base.CaseFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DatabaseCleanUp implements InitializingBean {

  private final EntityManager entityManager;

  private List<String> tableNames;

  @Override
  public void afterPropertiesSet() {
    tableNames = entityManager.getMetamodel().getEntities().stream()
        .filter(entityType -> entityType.getJavaType().getAnnotation(Entity.class) != null)
        .map(entityType -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.getName()))
        .collect(Collectors.toList());
  }

  @Transactional
  public void truncateAllEntity() {
    entityManager.flush();
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
    for (String tableName : tableNames) {
      entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
    }
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
  }

}