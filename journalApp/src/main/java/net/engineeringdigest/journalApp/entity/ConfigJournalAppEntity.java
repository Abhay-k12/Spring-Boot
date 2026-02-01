package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Data
@Document("config_journal_app")
public class ConfigJournalAppEntity {

    private String key;
    private String value;

}
