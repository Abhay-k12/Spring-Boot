package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Here we'll have that class which will aggregate to repository package class and habe some CRUD methods.
// These CRUD functions will then call the inbuilt Function provided by repository package
//BUSINESS LOGIC IS WRITTEN HERE

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        User currentUser = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);

        //Faulty code to study transaction
        //currentUser.setUserName(null);

        currentUser.getJournalEntries().add(saved);
        userService.saveEntry(currentUser);
    }

    public List<JournalEntry> getAll() {return journalEntryRepository.findAll();}

    public Optional<JournalEntry> getById(ObjectId id) {return journalEntryRepository.findById(id);}

    public void deleteObjectById(ObjectId id, String userName) {
        User currentUser = userService.findByUserName(userName);
        JournalEntry deleted = (JournalEntry) currentUser.getJournalEntries().stream().filter(x->x.getId().equals(id)).toList().get(0);

        currentUser.getJournalEntries().removeIf(x->x.getId().equals(deleted.getId()));
        userService.saveEntry(currentUser);

        journalEntryRepository.deleteById(deleted.getId());
    }

    public void saveEntry(JournalEntry journalEntry) {
        JournalEntry saved = journalEntryRepository.save(journalEntry);
    }
}
