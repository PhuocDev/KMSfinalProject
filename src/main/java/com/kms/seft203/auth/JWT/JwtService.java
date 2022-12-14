package com.kms.seft203.auth.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.auth.user.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Autowired
    public JwtService(@Value("${jwt.secret}") String jwtSecret, @Value("${jwt.issuer}") String issuer)
    {
        this.jwtSecret = jwtSecret;
        this.issuer = issuer;
    }
    private final static int timeExpired = 3*60*60*1000;
    private final static int refreshTokenLength = 50;

    public String createUserToken(User user)
    {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.create().withIssuer(issuer)
                .withClaim("id",user.getId())
                .withClaim("username",user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + timeExpired))
                .sign(algorithm);
    }

    public String generateRefreshToken()
    {
        return UUID.randomUUID().toString();
    }

    public DecodedJWT decodedJWT(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        return JWT.require(algorithm).withIssuer(issuer).build().verify(token);
    }
    public Map<String,String> getInfoFromJWT(String token)
    {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        //System.out.println("payload "+payload);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = new HashMap<String,String>();
        try {
            jsonMap = mapper.readValue(payload, new TypeReference<Map<String,String>>(){
                });
            } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //System.out.println("jsonMap "+jsonMap.get("username"));
        return jsonMap;
    }

        public boolean validateToken(String authToken) {
        try {
            //ki???m tra sign key (ph???n cu???i c???a jwt) c?? h???p l??? hay kh??ng b???ng seccret
            // n???u kh??ng validate signkey c?? th??? thay ?????i ???????c ph???n sign c???a token nh???n v??o
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWT.require(algorithm).withIssuer(issuer).build().verify(authToken);
            System.out.println("From validateToken in jwtService: Token h???p l???");
            //Jwts.parser().setSigningKey(String.valueOf(algorithm)).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: {}" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: {}" + e.getMessage());
        }
        return false;
    }
}
