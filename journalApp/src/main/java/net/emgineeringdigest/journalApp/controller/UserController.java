package net.emgineeringdigest.journalApp.controller;

import net.emgineeringdigest.journalApp.service.UserService;
import net.emgineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /* ----------Since One single user shouldn't get all the users, so deleting this controller--------
        @GetMapping
        public ResponseEntity<List<User>> getAllUsers() {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
          }
     */

    @PutMapping
    public ResponseEntity<?> changeUserNameOrPassword(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User currentUser = userService.findByUserName(userName);  //We can also use id here, but as userName is also Unique so it's also feasible, also header give us username.

        if(currentUser != null) {
            currentUser.setUserName(user.getUserName());
            currentUser.setPassword(user.getPassword());
            userService.saveNewEntry(currentUser);   //Replaces the old user having same id, with this currentUser in DB.
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Such User Found", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userService.deleteByUserName(authentication.getName());
            return new ResponseEntity<>("User Deleted Succesfully.", HttpStatus.ACCEPTED);
        }
        catch(Exception e) {
            return new ResponseEntity<>("Error:" + e.toString(), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}

