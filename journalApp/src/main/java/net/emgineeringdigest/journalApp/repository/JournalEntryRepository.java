package net.emgineeringdigest.journalApp.repository;

import net.emgineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

// Here We'll have that interface which will extend from the Persistence Provider.
//This interface will provide the inbuilt functions to the service package class.

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
