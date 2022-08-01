package com.spring.boot.domain;

import java.util.Arrays;
import java.util.Optional;

public enum RoleName {

    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    RoleName(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Optional<RoleName> of(String name){
        return Arrays.stream(RoleName.values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findAny();
    }
}
