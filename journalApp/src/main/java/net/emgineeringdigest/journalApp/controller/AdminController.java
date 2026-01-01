package net.emgineeringdigest.journalApp.controller;

import net.emgineeringdigest.journalApp.entity.JournalEntry;
import net.emgineeringdigest.journalApp.entity.User;
import net.emgineeringdigest.journalApp.service.JournalEntryService;
import net.emgineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-entries")
    public ResponseEntity<?> getAllEntries() {
        List<JournalEntry> list  = journalEntryService.getAll();
        if(!list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.OK);
        return new ResponseEntity<>("No Entry Exist", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> user = userService.getAll();
        if(!user.isEmpty())
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>("No Entry Exist", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(@RequestBody User user) {
        try {
            userService.saveAdmin(user);
            return new ResponseEntity<>("Admin Created Successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
    @PutMapping("/create-user-admin/{username}")
    public ResponseEntity<String> makeUserAdmin(@PathVariable String username) {
        User user = userService.findByUserName(username);

        if(user != null) {
            user.getRoles().add("ADMIN");
            userService.saveEntry(user);
            return new ResponseEntity<>("User Has Been Given Admin Tag", HttpStatus.OK);
        }
        return new ResponseEntity<>("No Such User Exist",HttpStatus.NOT_ACCEPTABLE);
    }
    */
}
