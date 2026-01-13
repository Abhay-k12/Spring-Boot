package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);

    void deleteByUserName(String userName);
    /*
        Why does this work without a method body?
        Answer: Spring Data MongoDB generates the method implementation at runtime using method name parsing (query derivation).
     */
}
