package com.myblog.demo.security;

public class JwtAuthResponseDTO {
    private  String tokenAccess;
    private  String tokenType = "Bearer ";

    public  JwtAuthResponseDTO(String token)
    {
        super();
        tokenAccess = token;
    }
    public String getTokenAccess() {
        return tokenAccess;
    }

    public void setTokenAccess(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
