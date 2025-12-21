package net.emgineeringdigest.journalApp.controller;

import net.emgineeringdigest.journalApp.entity.JournalEntry;
import net.emgineeringdigest.journalApp.entity.User;
import net.emgineeringdigest.journalApp.service.JournalEntryService;
import net.emgineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
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

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry obj, @PathVariable String userName) {
        try {
            obj.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(obj, userName);
            return new ResponseEntity<>(obj, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")  // getting the path variable (accessing the list item through id, that was given in path variable)
    public ResponseEntity<?> getElementById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> currentEntry = journalEntryService.getById(myId);

        if(currentEntry.isPresent()) {
            return new ResponseEntity<>(currentEntry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Esa kuch bhi nahi h db mein",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<String> deleteRecordThroughId(@PathVariable ObjectId myId, @PathVariable String userName) {
        try {
            /*
                Delete from journal entry will not delete it from user entry list, so manually have to tackle this.
                That's wjy we have taken username as well to search the user and remove this entry from his list.
                (Although next time while saving a new entry, mongo will delete it automatically, but prefer to delete it on spot);
             */
            journalEntryService.deleteObjectById(myId,userName);
            return new ResponseEntity<>("Deleted Successfully From Records", HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/id/{userName}/{myId}")  // path variable + body content to update the corresponding object related to the id.
    public ResponseEntity<String> updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String userName) {
        JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);

        if(oldEntry !=null) {
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());

            //User hold the reference of the entry, so no need to alter anything in user.
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>( "Updated!", HttpStatus.OK);
        }
        return new ResponseEntity<>("hupp nahi h ye db m!", HttpStatus.NOT_FOUND);
    }
};
