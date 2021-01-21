package com.puwd.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @auther puwd
 * @Date 2021-1-20 15:01
 * @Description
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private boolean enable = false;

    private long expire = 24 * 60 *60;

    private String secret;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
