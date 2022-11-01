package com.kms.seft203.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kms.seft203.auth.DTO.LoginRequest;
import com.kms.seft203.auth.DTO.LoginResponse;
import com.kms.seft203.auth.DTO.LogoutRequest;
import com.kms.seft203.auth.DTO.RegisterRequest;
import com.kms.seft203.auth.login.JwtTokenProvider;
import com.kms.seft203.auth.user.User;
import com.kms.seft203.auth.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthApi {

    private static final Map<String, User> DATA = new HashMap<>();
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public User register(@RequestBody @Valid RegisterRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName()
        );
        userService.insertNewUser(user);
        //DATA.put(user.getUsername(), user);
        // TODO: remove user's password
        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        System.out.println("phuoc");
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Authentication comleted!");
        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new ResponseEntity<String>(jwt, HttpStatus.OK);

    }

    private String createJwtToken(String username, String displayName) {
        try {
            String JWT_SECRET = "phuoc123";
            return Jwts.builder()
                    .setSubject(String.format(username))
                    .setIssuer("kms")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                    .compact();
        } catch (JWTCreationException ex) {
            System.out.println(ex);
            return "";
        }
    }

    @GetMapping("/logout")
    public String logout(@RequestBody LogoutRequest request) {
        return "Logged out";
    }
}
