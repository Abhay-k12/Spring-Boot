package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// Here We'll have that interface which will extend from the Persistence Provider.
//This interface will provide the inbuilt functions to the service package class.

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
