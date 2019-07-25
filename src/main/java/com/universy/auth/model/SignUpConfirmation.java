package com.universy.auth.model;

public class SignUpConfirmation implements UsernameContainer {
    private String username;
    private String code;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
