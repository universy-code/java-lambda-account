package com.universy.auth.model;

public class SignUpConfirmation extends User {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
