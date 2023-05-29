package org.mohsoft.model;

public class CsrfTokenDto {
    private String token;

    public CsrfTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
