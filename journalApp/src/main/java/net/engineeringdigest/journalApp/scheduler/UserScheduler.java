package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    AppCache appCache;

    //@Scheduled(cron = "0 0 9 * * SUN")
    /* 6 field denotes: (second minute hours dayOfMonth Month DayOfTheWeek)  */
    public void fetchUsersForSentimentAnalysisAndMailSending() {
        List<User> users = userRepository.getUsersForSentimentAnalysis();

        for(User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentsCount = new HashMap<>();

            for(Sentiment sentiment : sentiments) {
                if(sentiment!=null)
                    sentimentsCount.put(sentiment, sentimentsCount.getOrDefault(sentiment,0)+1);
            }

            Sentiment mostFrequencySentiment = null;
            int maxCount = 0;

            for(Map.Entry<Sentiment,Integer> entry : sentimentsCount.entrySet()) {
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequencySentiment = entry.getKey();
                }
            }

            if(mostFrequencySentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment Analysis for last 7 days: ", mostFrequencySentiment.toString());
            }
        }
    }

    /* This will refresh the cache in the specified unit of time, that is 10mins*/
    @Scheduled( cron = "0 0/10 * ? * *")
    public void updateAppCache() {
        appCache.init();
    }
}
