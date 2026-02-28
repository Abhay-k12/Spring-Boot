package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Disabled
@SpringBootTest
public class SentimentAnalysisTest {

    @Autowired
    UserScheduler userScheduler = new UserScheduler();

    @MockBean
    private UserRepositoryImpl userRepository;

    @MockBean
    private EmailService emailService;

    @Test
    public void sentimentAnalysisTestForUser() {
        userScheduler.fetchUsersForSentimentAnalysisAndMailSending();
    }
}
