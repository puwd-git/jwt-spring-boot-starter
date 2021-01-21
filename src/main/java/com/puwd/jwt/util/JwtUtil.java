package com.puwd.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @auther puwd
 * @Date 2021-1-20 15:31
 * @Description
 */
public class JwtUtil {

    public static String createToken(Object user) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + 24 * 60 *60);
        Algorithm algorithm = Algorithm.HMAC256("123");
        return JWT.create()
                .withExpiresAt(date)
                .withClaim("userId", 123L)
                .withClaim("username", "test")
                .withClaim("age", 22)
                .withClaim("depart", 100)
                .withSubject("123")
                .sign(algorithm);
    }

    public static String getUserId(String jwtToken){
        return JWT.decode(jwtToken).getSubject();
    }

    public static Map<String, Claim> getClaims(String jwtToken){
        try {
            Algorithm algorithm = Algorithm.HMAC256("123");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(jwtToken);
            return jwt.getClaims();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
