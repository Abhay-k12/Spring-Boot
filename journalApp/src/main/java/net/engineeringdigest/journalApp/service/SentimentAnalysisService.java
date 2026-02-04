package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

    public String getSentimentAnalysis(String text) {
        return "happy happy happy";
    }
}
