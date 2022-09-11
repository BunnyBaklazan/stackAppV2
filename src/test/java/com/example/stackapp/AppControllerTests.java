package com.example.stackapp;

import org.junit.jupiter.api.*;
import com.example.stackapp.controller.AppController;

public class AppControllerTests {
    @Test
    void isValidCredentialsTest() {
        Assertions.assertFalse(AppController.isValidCredentials(" ", " "));
        Assertions.assertFalse(AppController.isValidCredentials("mscott", ""));
        Assertions.assertFalse(AppController.isValidCredentials("mscott", "     "));
        Assertions.assertFalse(AppController.isValidCredentials("  ", "[#@bkhkgkgkgk]"));
        Assertions.assertTrue(AppController.isValidCredentials("mscott", "@"));
    }
}
