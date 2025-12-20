package net.emgineeringdigest.journalApp.controller;

import net.emgineeringdigest.journalApp.entity.Users;
import net.emgineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>("User Created Succcessfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> changeUserNameOrPassword(@RequestBody Users user) {
        Users currentUser = userService.findByUserName(user.getUserName());  //We can also user Id here, but as userName is also Unique so it's also feasible.

        if(currentUser != null) {
            currentUser.setUserName(user.getUserName());
            currentUser.setPassword(user.getPassword());
            userService.saveEntry(currentUser);   //Replaces the old user having same id, with this currentUser in DB.
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Such User Found", HttpStatus.NO_CONTENT);
    }

};

