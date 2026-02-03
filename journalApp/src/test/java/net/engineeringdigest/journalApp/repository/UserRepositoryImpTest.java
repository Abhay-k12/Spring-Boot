package net.engineeringdigest.journalApp.repository;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
public class UserRepositoryImpTest {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void getUserForSentimentAnalysisTestTest() {
        assertEquals(1, userRepository.getUsersForSentimentAnalysis().size());
    }
}
