package net.emgineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
/*
    //If Collection doesn't exist then it will automatically create it, but to generate index automatically
    you have to add ("spring.data.mongodb.auto-index-creation=true") in application.properties
*/

@Data
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;   //Indexed annotation is given to make this field unique.

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    /*
        With this @DBRef annotation we have made our User to link with the JournalEntry through reference
        This is very similar to the foreign key that we have studied in SQL, in which this List is having the
        reference of the id's in JournalEntries (journal_entries in DB).
     */
}
