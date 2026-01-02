package net.emgineeringdigest.journalApp.service;

import net.emgineeringdigest.journalApp.entity.User;
import net.emgineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest   //So that Spring will create component and beans (which was previously created by @SpringBootApplication annotation);
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByUsernameTest() {
        assertNotNull(userRepository.findByUserName("Abhay"));
        assertEquals(4,3+1);
    }
}
