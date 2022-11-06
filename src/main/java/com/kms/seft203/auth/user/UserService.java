package com.kms.seft203.auth.user;

import com.kms.seft203.auth.DTO.LoginRequest;
import com.kms.seft203.auth.DTO.LoginResponse;
import com.kms.seft203.auth.DTO.LogoutRequest;
import com.kms.seft203.auth.DTO.RegisterRequest;
import com.kms.seft203.auth.JWT.JwtService;
import com.kms.seft203.auth.JWT.UserJwt;
import com.kms.seft203.auth.JWT.UserJwtRepository;
import com.kms.seft203.exception.DataNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserJwtRepository userJwtRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${mail.from-email}")
    private String fromEmail;

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        //find duplicated username
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            throw new DataNotFoundException("Username can not be found");
        }
        User user = userRepository.findByUsername(loginRequest.getUsername());
        //check password
        var encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(loginRequest.getPassword(),user.getPassword()))
        {
            throw new DataNotFoundException("Password do not correct");
        }
        // login successful
        // create access token and refresh token
        var token =jwtService.createUserToken(user);
        var userJwt = userJwtRepository.findByUserId(user.getId());
        var refreshToken = userJwt.isEmpty() ? jwtService.generateRefreshToken(): userJwt.get().getRefreshToken();
        if(userJwt.isEmpty())
        {
            userJwtRepository.save(new UserJwt(0L,true,refreshToken,user));
        }
        return new LoginResponse(
                token,
                refreshToken
        );
    }
    public String getNewToken(User user) {
        // create access token and refresh token (if not have)
        var token =jwtService.createUserToken(user);
        var userJwt = userJwtRepository.findByUserId(user.getId());
        var refreshToken = userJwt.isEmpty() ? jwtService.generateRefreshToken(): userJwt.get().getRefreshToken();
        if(userJwt.isEmpty())
        {
            userJwtRepository.save(new UserJwt(0L,true,refreshToken,user));
        }
        return token;
    }
    public User register(RegisterRequest registerRequest, String request) throws Exception {
        if(userRepository.existsByUsername(registerRequest.getUsername()))
        {
            throw new Exception("username is already registered");
        }
        User user = new User(registerRequest); // create user but password still not encode
        System.out.println("User created " + user.toString());
        if(user != null)
        {
            String randomCode = RandomString.make(64);
            user.setVerificationcode(randomCode);
            user.setEnabled(false);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            sendVerificationEmail(user,request);
            return userRepository.save(user);
        }

        return null;

    }
    public void sendVerificationEmail(User user,String siteUrl) throws MessagingException, UnsupportedEncodingException {
        System.out.println("siteUrl "+ siteUrl);
        String toAddress=user.getEmail();
        String fromAddress= fromEmail;
        System.out.println("address send mail "+ fromAddress + " to "+toAddress);
        String senderName = fromEmail + "verification";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";  // need pass name, url
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyUrl = siteUrl + "/auth/verify?code="+user.getVerificationcode();

        content = content.replace("[[name]]", user.getFullName());
        content = content.replace("[[URL]]",verifyUrl);

        helper.setText(content, true);

        javaMailSender.send(message);

    }

    public void logout(LogoutRequest logoutRequest) {
        String id = userRepository.findByUsername(logoutRequest.getUserName()).getId();
        UserJwt userJwt = userJwtRepository.findByUserId(id).orElse(null);
        assert userJwt != null : "User not found";
        userJwtRepository.delete(userJwt);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
    public User findUserById(String id) {
        return userRepository.findById(id).get();
    }
    public User insertNewUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

    public void updateUser(String id, User newUser) {
        User oldUser = userRepository.findById(id).get();
        oldUser.setEmail(newUser.getEmail());
        oldUser.setFullName(newUser.getFullName());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());

        userRepository.saveAndFlush(oldUser);
    }

    public boolean isUserIdExist(String userID) {
        return userRepository.existsById(userID);
    }

    public boolean checkLogin(String username, String password) {
        if (userRepository.existsByUsername(username) && userRepository.existsByPassword(password)) {
            return true;
        } else return false;
    }

    public User findUserByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    public UserDetails loadUserById(String userId) {
        return new CustomUserDetails(userRepository.findById(userId).get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationcode(verificationCode);

        if(user ==null)
        {
            return false;
        }
        user.setVerificationcode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }
}
