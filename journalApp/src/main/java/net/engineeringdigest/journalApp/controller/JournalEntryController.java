package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("get-journal")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            User currentUser = userService.findByUserName(userName);
            List<JournalEntry> journalEntriesList = currentUser.getJournalEntries();
            if(journalEntriesList !=null && !journalEntriesList.isEmpty())
                return new ResponseEntity<>(journalEntriesList, HttpStatus.OK);
            else
                return new ResponseEntity<>("No Entry Is Associated with this user",HttpStatus.NO_CONTENT);
        }
        catch(Exception e) {
            return new ResponseEntity<>("No Such User Exist", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("create-journal")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry obj) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            journalEntryService.saveEntry(obj, userName);
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Something Went Wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> list = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if(!list.isEmpty())  {
            Optional<JournalEntry> currentEntry = journalEntryService.getById(myId);
            if(currentEntry.isPresent()) {
                return new ResponseEntity<>(currentEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Apni Journal ki Id de, dusre ki q dekhra hai ??",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-journal/{myId}")
    public ResponseEntity<String> deleteRecordThroughId(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        try {
            /*
                Delete from journal entry will not delete it from user entry list, so manually have to tackle this.
                That's why we have taken username as well to search the user and remove this entry from his list.
                (Although next time while saving a new entry, mongo will delete it automatically, but prefer to delete it on spot);
             */
            journalEntryService.deleteObjectById(myId,userName);
            return new ResponseEntity<>("Deleted Successfully From Records", HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update-journal/{myId}")  // path variable + body content to update the corresponding object related to the id.
    public ResponseEntity<String> updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        JournalEntry oldEntry = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList().get(0);   // filter always returns either true or exception

        oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
        oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
        journalEntryService.saveEntry(oldEntry);   //User hold the reference of the entry, so no need to alter anything in user.
        return new ResponseEntity<>( "Updated!", HttpStatus.OK);
    }
};
