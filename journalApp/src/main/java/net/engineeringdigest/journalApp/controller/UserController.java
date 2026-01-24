package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> changeUserNameOrPassword(@RequestBody User user) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User currentUser = userService.findByUserName(userName);  //We can also use id here, but as userName is also Unique so it's also feasible, also header give us username.

        if(currentUser != null) {
            currentUser.setUserName(user.getUserName());
            currentUser.setPassword(user.getPassword());
            boolean result = userService.saveNewEntry(currentUser);   //Replaces the old user having same id, with this currentUser in DB.
            return new ResponseEntity<>("User Details are updated", HttpStatus.OK);
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

    @GetMapping("/hello")
    public ResponseEntity<String> sayHelloWithWaether() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse body = weatherService.getWeather("Mumbai");

        if(body != null) {
            return new ResponseEntity<>("Hello" +authentication.getName()+ body.toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Hello" + authentication.getName() + "NO INFO ABOUT WEATHER", HttpStatus.NO_CONTENT);
    }
}

