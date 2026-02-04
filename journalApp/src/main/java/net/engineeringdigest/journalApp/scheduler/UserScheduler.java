package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    public void fetchUsersForSentimentAnalysisAndMailSending() {
        List<User> users = userRepository.getUsersForSentimentAnalysis();

        for(User user: users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntry::getContent).toList();
            String entries = String.join(" ",filteredEntries);
            String sentiment = sentimentAnalysisService.getSentimentAnalysis(entries);
            emailService.sendEmail(user.getEmail(), "Sentiment Analysis for last 7 days.", sentiment);
        }
    }
}
