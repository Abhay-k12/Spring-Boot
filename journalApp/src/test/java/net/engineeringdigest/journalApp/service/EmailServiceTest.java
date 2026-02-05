package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

@Disabled
@SpringBootTest
@Slf4j
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @ParameterizedTest
    @CsvSource({
            "abcd@gmail.com, Sample Message, Congratulations!! Hii, hope are you doing?" ,
            "helloJavaSpringBoot@gmail.com", "Thank you to help me", "Learning you is the onr of the good decisions"
    })
    public void testMail(String to, String subject, String body) {
        try {
            emailService.sendEmail(to,subject,body);
        }
        catch(Exception e) {
            System.out.println("Exception:" + e);
        }
    }

    @Test
    public void testMail() {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            emailService.sendEmail("abcd@gmail.com","Leaning SpringBoot", "Learn SpringBoot and make Projects");
        }
        catch(Exception e) {
            log.error("Exception :", e);
        }
    }
}
