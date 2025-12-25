package net.emgineeringdigest.journalApp.controller;

import net.emgineeringdigest.journalApp.entity.User;
import net.emgineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String SystemHeathCheckUp() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.saveNewEntry(user);
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
};