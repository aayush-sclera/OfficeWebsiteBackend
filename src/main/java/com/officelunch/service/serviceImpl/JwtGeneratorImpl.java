package com.officelunch.service.serviceImpl;

import com.officelunch.model.User;
import com.officelunch.service.JwtGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGeneratorImpl implements JwtGenerator {
    private String secret;
    private String message;

    @Override
    public Map<String, String> generateToken(User user) {

        String jwtToken = "";
        jwtToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*2))
                .signWith(SignatureAlgorithm.HS256,"secret")
                .compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token",jwtToken);
        jwtTokenGen.put("message", message);
        return jwtTokenGen;

    }
}
