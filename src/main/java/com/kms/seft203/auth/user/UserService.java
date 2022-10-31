package com.kms.seft203.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
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

}
