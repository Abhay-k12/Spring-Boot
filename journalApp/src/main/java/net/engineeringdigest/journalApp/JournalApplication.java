package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JournalApplication {

	public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


/*
    Logging:
    2026-01-08T23:05:05.583+05:30  INFO 1180 --- [           main] n.e.journalApp.JournalApplication        : Starting JournalApplication using Java 23.0.2 with PID 1180
        ^                           ^     ^                             ^                                             ^
        |                           |     |                             |                                             |
    (Time stamp)          (Log Severity) (Process Id)           (Thread Identifier)                                 (Log)
 */