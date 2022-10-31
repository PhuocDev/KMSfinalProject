package com.kms.seft203.auth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/createDB")
    public List<User> createDB() {
        for (int i = 1; i < 5; i++) {
            User user = new User("Username" + i, "Email+" + i, "pass" + i*567344,"Fullname" + i );
            userService.insertNewUser(user);
        }
        return userService.getUsers();
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<?> insertUser(@RequestBody @Valid User newUser) {
        if (newUser!= null) {
            userService.insertNewUser(newUser);
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        } else return new ResponseEntity<String>( "Can not insert user", HttpStatus.BAD_REQUEST);
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
