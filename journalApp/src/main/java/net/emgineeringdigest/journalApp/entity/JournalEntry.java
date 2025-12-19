package net.emgineeringdigest.journalApp.entity;

// THESE TYPE OF CLASS IS CALLED POJO CLASS - PLAIN OLD JAVA OBJECT

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection =  "journalEntry") //Mapped to that collection ofMongoDB, whose type is held by the POJO
public class JournalEntry {

    @Id
    private ObjectId id;

    private String title;

    private String content;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    private LocalDateTime date;

    public ObjectId getId() {
        return id;
    }

    public String getTitle() { return title; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(ObjectId id) { this.id = id; }

}
