package net.emgineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// built just for checking that the system is working fine or not.
@RestController
public class HealthCheck {
    @GetMapping("/health-check")
    public String SystemHeathCheckUp() {
        return "Ok";
    }
};