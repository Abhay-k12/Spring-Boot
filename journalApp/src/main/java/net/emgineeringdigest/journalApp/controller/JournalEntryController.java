package net.emgineeringdigest.journalApp.controller;

import net.emgineeringdigest.journalApp.entity.JournalEntry;
import net.emgineeringdigest.journalApp.service.JournalEntryService;
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
    private JournalEntryService journalEntryService; // make sure make this class as bean (using @Component annotation);

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries() {
        return new ResponseEntity<>(journalEntryService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry obj) {
        try {
            obj.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(obj);
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

    @DeleteMapping("/id/{myId}")  // function accepts path variable and deleted that record.
    public ResponseEntity<String> deleteRecordThroughId(@PathVariable ObjectId myId) {
        Optional<JournalEntry> currentEntry = journalEntryService.getById(myId);
        if(currentEntry.isPresent()) {
            return new ResponseEntity<>("Yeah! You have deleted the object.", HttpStatus.OK);
        }
        return new ResponseEntity<>("abe hai hi nahi, delete kese karu??", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")  // path variable + body content to update the corresponding object related to the id.
    public ResponseEntity<String> updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);
        if(oldEntry !=null) {
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>( "Updated!", HttpStatus.OK);
        }
        return new ResponseEntity<>("hupp nahi h ye db m!", HttpStatus.NOT_FOUND);
    }
};
