package com.puwd.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.puwd.jwt.dto.JwtUserDto;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @auther puwd
 * @Date 2021-1-20 15:31
 * @Description
 */
public class JwtUtil {

    private static final String SECRET = "jwt.secret.random.";

    public static final String TOKEN_KEY = "JWT_TOKEN";

    public static String createToken(JwtUserDto user) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("userId", user.getUserId())
                .withClaim("userName", user.getUserName())
                .withClaim("realName", user.getRealName())
                .withClaim("age", user.getAge())
                .withClaim("gender", user.getGender())
                .withSubject(user.getUserId())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public static String createToken(JwtUserDto user, Long expire) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + expire);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withClaim("userId", user.getUserId())
                .withClaim("userName", user.getUserName())
                .withClaim("realName", user.getRealName())
                .withClaim("age", user.getAge())
                .withClaim("gender", user.getGender())
                .withSubject(user.getUserId())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public static String getUserId(String jwtToken){
        return JWT.decode(jwtToken).getSubject();
    }

    public static Date getExpiresAt(String jwtToken){
        return JWT.decode(jwtToken).getExpiresAt();
    }

    public static Map<String, Claim> getClaims(String jwtToken) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(jwtToken);
        return jwt.getClaims();
    }
}
