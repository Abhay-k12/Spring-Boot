package net.engineeringdigest.journalApp.cache;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {
        List<ConfigJournalAppEntity> all= configJournalAppRepository.findAll();

        for(ConfigJournalAppEntity entry: all) {
            APP_CACHE.put(entry.getKey(), entry.getValue());
        }
    }
}
