package com.spring.boot.domain;

import java.util.Arrays;
import java.util.Optional;

public enum Role {

    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static Optional<Role> of(String name){
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findAny();
    }
}
