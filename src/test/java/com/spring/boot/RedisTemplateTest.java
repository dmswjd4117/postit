package com.spring.boot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@MockBean(JpaMetamodelMappingContext.class)
@DataRedisTest
class RedisTemplateTest {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Test
  void opsForValue() {
    // given
    String key = "key";
    String value = "value";
    redisTemplate.delete(key);

    // when
    ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

    stringStringValueOperations.set(key, value);
    String result = stringStringValueOperations.get(key);

    // then
    assertThat(result, is(value));
  }
}