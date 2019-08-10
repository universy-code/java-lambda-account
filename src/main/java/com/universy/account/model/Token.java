package com.universy.account.model;

public class Token {
    private final String username;
    private final String idToken;
    private final String accessToken;
    private final String refreshToken;

    public Token(String username, String idToken, String accessToken, String refreshToken) {
        this.username = username;
        this.idToken = idToken;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
