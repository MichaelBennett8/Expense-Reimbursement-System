package dev.bennett.utiltests;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.bennett.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JwtTests {

    @Test
    void createJWT(){
        String jwt1 = JwtUtil.generate("EMPLOYEE","Mary Sue");
        System.out.println(jwt1);

        String jwt2 = JwtUtil.generate("MANAGER","Mary Sue");
        System.out.println(jwt2);

        Assertions.assertNotEquals(jwt1, jwt2);

        System.out.println(JwtUtil.generate("MANAGER","Sophie"));
    }

    @Test
    void decodeJWT(){
        DecodedJWT jwt = JwtUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJNYXJ5IFN1ZSIsInRpdGxlIjoiRU1QTE9ZRUUifQ.Lft2FU6enQpuTmuhPh9nM0Km236D4gtgddnNeqYXuNY");
        String role = jwt.getClaim("title").asString();
        System.out.println(role);

        Assertions.assertEquals("EMPLOYEE", role);

        jwt = JwtUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJTb3BoaWUiLCJ0aXRsZSI6Ik1BTkFHRVIifQ.6fDlA9Yd4TAxMdJHuvTJgiE6jPSlZdOJ19JBzJ3M8d0");
        role = jwt.getClaim("title").asString();
        System.out.println(role);

        Assertions.assertEquals("MANAGER", role);

        jwt = JwtUtil.isValidJWT("super.Legit.JWT");
        Assertions.assertNull(jwt);

        jwt = JwtUtil.isValidJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbXBsb3llZU5hbWUiOiJNYXZlcmljayIsInRpdGxlIjoiRU1QTE9ZRUUifQ.I-PFOcBZFEQHwtavNPSdFL25l6YxWlQfae8drNpabUw");
        String name = jwt.getClaim("employeeName").asString();
        Assertions.assertEquals("Maverick", name);
    }
}
