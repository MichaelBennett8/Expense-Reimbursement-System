package dev.bennett.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.bennett.entities.JobTitle;

public class JwtUtil {

    private static final String secret = System.getenv("JWT_SECRET");
    private static final Algorithm algorithm = Algorithm.HMAC256(secret);

    public static String generate(String title, String employeeName){

        String token = JWT.create()
                .withClaim("title", title)
                .withClaim("employeeName", employeeName)
                .sign(algorithm);

        return token;
    }

    public static DecodedJWT isValidJWT(String token){
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return jwt;
        }
        catch (JWTDecodeException e){
            System.out.println("Invalid JWT");
            return null;
        }
    }
}
