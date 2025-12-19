package net.emgineeringdigest.journalApp.service;

import net.emgineeringdigest.journalApp.entity.JournalEntry;
import net.emgineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


// Here we'll have that class which will aggregate to repository package class and habe some CRUD methods.
// These CRUD functions will then call the inbuilt Function provided by repository package

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {return journalEntryRepository.findAll();}

    public Optional<JournalEntry> getById(ObjectId id) {return journalEntryRepository.findById(id);}

    public void deleteObjectById(ObjectId id) {journalEntryRepository.deleteById(id);}

}
