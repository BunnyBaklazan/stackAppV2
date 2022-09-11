package com.example.stackapp;

import org.junit.jupiter.api.*;
import com.example.stackapp.controller.SignInController;

public class SignInControllerTests {
    @Test
    void isValidCredentialsTest() {
        Assertions.assertFalse(SignInController.isValidCredentials(" ", " "));
        Assertions.assertFalse(SignInController.isValidCredentials("mscott", ""));
        Assertions.assertFalse(SignInController.isValidCredentials("mscott", "     "));
        Assertions.assertFalse(SignInController.isValidCredentials("  ", "[#@bkhkgkgkgk]"));
        Assertions.assertTrue(SignInController.isValidCredentials("mscott", "@"));
    }
}
