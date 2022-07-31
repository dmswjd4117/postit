package com.spring.boot.domain;

import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    @Test
    public void Role_이름찾기(){
        Optional<Role> role = Role.of("admIn");
        assertTrue(role.isPresent());
        assertEquals(role.get(), Role.ADMIN);
    }

    @Test
    public void Role_이름찾기실패(){
        Optional<Role> role = Role.of("test");
        assertFalse(role.isPresent());
    }
}