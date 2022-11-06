package com.kms.seft203.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        if (userService.findUserById(id) != null) {
            userService.deleteUserById(id);
            return new ResponseEntity<String>("Đã xóa!", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>( "Can not find user id: " + id, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") String id, @RequestBody User user) {
        if (userService.findUserById(id) != null) {
            userService.updateUser(id, user);
            return new ResponseEntity<User>(userService.findUserById(id), HttpStatus.OK);
        } else return new ResponseEntity<String>( "Can not find user id: " + id, HttpStatus.BAD_REQUEST);

    }
}
