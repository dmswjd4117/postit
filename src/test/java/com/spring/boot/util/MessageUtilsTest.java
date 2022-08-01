package com.spring.boot.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.ActiveProfiles;

import java.util.Locale;

@SpringBootTest
@ActiveProfiles("test")
class MessageUtilsTest {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Test
    public void 한국어_테스트(){
        System.out.println(
                messageSourceAccessor.getMessage("error.duplicated.details", new String[]{"email"}));
    }

    @Test
    public void 영어_테스트(){
        System.out.println(
                messageSourceAccessor.getMessage("error.duplicated.details",new String[]{"email"}, Locale.US));
    }
}