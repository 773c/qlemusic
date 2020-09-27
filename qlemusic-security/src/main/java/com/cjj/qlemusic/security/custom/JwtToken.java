package com.cjj.qlemusic.security.custom;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 这个类相当于UsernamePasswordToken
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
