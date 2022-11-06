package com.kms.seft203.auth;

import com.kms.seft203.auth.DTO.*;
import com.kms.seft203.auth.JWT.UserJwtRepository;
import com.kms.seft203.auth.user.User;
import com.kms.seft203.auth.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    UserJwtRepository userJwtRepository;

    @Value("${mail.from-email}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;



    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest registerRequest, HttpServletRequest request)
            throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerRequest,getSiteUrl(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest));
    }
    @PostMapping("/logout")
    public ResponseEntity<Object>logout(@Valid @RequestBody LogoutRequest logoutRequest)
    {
        userService.logout(logoutRequest);
        return ResponseEntity.ok().body("Logout successfully");
    }
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code)
    {
        System.out.println("get verify "+code);
        if(userService.verify(code))
        {
            return "verify_success";
        }
        else {
            return "verify_fail";
        }
    }
    public String getSiteUrl(HttpServletRequest request)
    {
        // /auth/verify
        return request.getRequestURL().toString().replace(request.getRequestURI(),"");
    }

    @PostMapping("/refreshToken")
    public String getNewToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();
        String newToken = null;
        if (userJwtRepository.existsByRefreshToken(refreshToken)){
            User currentUser = userJwtRepository.findUserJwtByRefreshToken(refreshToken).getUser();
            newToken = userService.getNewToken(currentUser);
        }
        return "new token: " + newToken;
    }

}
