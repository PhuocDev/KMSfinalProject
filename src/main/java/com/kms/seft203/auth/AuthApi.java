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
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public User register(@RequestBody @Valid RegisterRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getFullName()
        );
        userService.insertNewUser(user);
        //DATA.put(user.getUsername(), user);
        // TODO: remove user's password
        return user;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
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
        System.out.println(authentication.getAuthorities());
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("step 2");
        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new ResponseEntity<String>(jwt, HttpStatus.OK);
//        if (userService.checkLogin(request.getUsername(), request.getPassword()))
//        {
//            LoginResponse loginResponse = new LoginResponse(createJwtToken(request.getUsername(), "User/Admin"), "<refresh_token>");
//            return ResponseEntity.ok(loginResponse);
//        } else return new ResponseEntity<>("Error",HttpStatus.BAD_REQUEST);
//


//        String username = null, password = null;
//        if (userService.checkLogin(request.getUsername(), request.getPassword())) {
//            System.out.println("username va mat khau dung");
//            username = request.getUsername();
//            password = request.getPassword();
//        }
//        Authentication authentication =  authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username,password)
//            );
//        if (userService.checkLogin(request.getUsername(), request.getPassword())) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            LoginResponse loginResponse = new LoginResponse(createJwtToken(request.getUsername(), "User/Admin"), "<refresh_token>");
//            return ResponseEntity.ok(loginResponse);
//        } else return new ResponseEntity<>("Error",HttpStatus.BAD_REQUEST);
//        User user = DATA.get(request.getUsername());
//        if (user != null && user.getPassword().equals(request.getPassword())) {
//            LoginResponse loginResponse = new LoginResponse(
//                    createJwtToken(request.getUsername(), user.getFullName()), "<refresh_token>");
//            return ResponseEntity.ok(loginResponse);
//        }
        //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    private String createJwtToken(String username, String displayName) {
        try {
            //Algorithm algorithm = Algorithm.HMAC256("this is a secret");
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

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
    }
}
