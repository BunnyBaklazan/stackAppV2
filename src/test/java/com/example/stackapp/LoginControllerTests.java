package com.example.stackapp;

import org.junit.jupiter.api.*;
import com.example.stackapp.controller.LoginController;

public class LoginControllerTests {
    @Test
    void isValidCredentialsTest() {
        Assertions.assertFalse(LoginController.isValidCredentials(" ", " "));
        Assertions.assertFalse(LoginController.isValidCredentials("mscott", ""));
        Assertions.assertFalse(LoginController.isValidCredentials("mscott", "     "));
        Assertions.assertFalse(LoginController.isValidCredentials("  ", "[#@bkhkgkgkgk]"));
        Assertions.assertTrue(LoginController.isValidCredentials("mscott", "@"));
    }
}
