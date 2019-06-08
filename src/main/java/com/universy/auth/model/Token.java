package com.universy.auth.model;

public class Token {
    private final String mail;
    private final String token;

    public Token(String mail, String token) {
        this.mail = mail;
        this.token = token;
    }

    public String getMail() {
        return mail;
    }


    public String getToken() {
        return token;
    }
}
