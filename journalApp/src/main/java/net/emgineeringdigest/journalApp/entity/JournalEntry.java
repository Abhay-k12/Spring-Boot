package net.emgineeringdigest.journalApp.entity;

// THESE TYPE OF CLASS IS CALLED POJO CLASS - PLAIN OLD JAVA OBJECT

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection =  "journalEntry") //Mapped to that collection ofMongoDB, whose type is held by the POJO
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
}
