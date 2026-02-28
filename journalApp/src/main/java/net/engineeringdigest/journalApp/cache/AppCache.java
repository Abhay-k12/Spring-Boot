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

    /*HERE WE CAN MAP OUR CONFIGS AS ENUM SO THAT EACH FIELD SHOULD REMAIN KNOWN AND TRACKED*/
    public enum Keys {
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String,String> APP_CACHE;

    @PostConstruct
    public void init() {
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all= configJournalAppRepository.findAll();

        for(ConfigJournalAppEntity entry: all) {
            APP_CACHE.put(entry.getKey(), entry.getValue());
        }
    }
}
