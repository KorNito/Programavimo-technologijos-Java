package com.example.lab6.ds;

import java.io.Serializable;

public class Company extends User implements Serializable {
    private String title;

    public Company(String login, String pass, String title) {
        super(login, pass);
        this.title = title;
    }
}
