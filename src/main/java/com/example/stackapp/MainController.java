package com.example.stackapp;

import java.io.IOException;

public class MainController {

    private static final String LOGIN_PAGE = "pages/log-in.fxml";
    private static final String SING_UP_PAGE = "pages/sign-up.fxml";
    private static final Main window = new Main(); //

    public void openLoginPage() throws IOException {
        System.out.println("TEST CODE OPEN LOGIC PAGE");
        window.changePage(LOGIN_PAGE);
    }

    public void shouldPrintAnytext()  throws IOException {
        System.out.println("OPEN SIGN UP PAGE");
        window.changePage(SING_UP_PAGE);
    }



}
