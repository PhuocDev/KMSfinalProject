package com.kms.seft203.auth.user;

import com.kms.seft203.auth.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

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
}
