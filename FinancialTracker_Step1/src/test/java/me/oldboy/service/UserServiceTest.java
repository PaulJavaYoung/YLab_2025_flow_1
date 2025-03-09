package me.oldboy.service;

import me.oldboy.entity.feature.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService = UserService.getInstance();

    @Test
    void shouldReturnUserRoleByGetUserRoleTest() {
        assertTrue(userService.getUserRole(1L).equals(Role.ADMIN));
        assertTrue(userService.getUserRole(2L).equals(Role.USER));
    }
}