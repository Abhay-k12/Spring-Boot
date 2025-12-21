package net.emgineeringdigest.journalApp.controller;

import net.emgineeringdigest.journalApp.service.UserService;
import net.emgineeringdigest.journalApp.entity.User;
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
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PutMapping("{userName}")
    public ResponseEntity<?> changeUserNameOrPassword(@RequestBody User user, @PathVariable String userName) {
        User currentUser = userService.findByUserName(userName);  //We can also use id here, but as userName is also Unique so it's also feasible.

        if(currentUser != null) {
            currentUser.setUserName(user.getUserName());
            currentUser.setPassword(user.getPassword());
            userService.saveEntry(currentUser);   //Replaces the old user having same id, with this currentUser in DB.
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Such User Found", HttpStatus.NO_CONTENT);
    }
}

