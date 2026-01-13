package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

// @SpringBootTest - This will be used in case we are making use of @Autowired and @MockBeans
public class CustomUserServiceImplTests {

    @InjectMocks
    private CustomUserServiceImpl userDetailsService;
    /* Because I want to test loadUserByUsername(String) method, so call it I want the object of that class */

    @Mock
    private UserRepository userRepository;
    /*  Since the loadUserByUsername(String) is calling userRepository, so we need bean of this type as well,
        During runtime the @InjectMocks private CustomUserServiceImpl userDetailsService; will be automatically initialized
        But the @Mock private UserRepository userRepository; will remain as null since things are not autowired here,
        That's why we need a function to initialize this mocks.*/

    @BeforeEach
    void setupMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsernameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder().userName("Abhay").password("abhay").roles(new ArrayList<>()).build());
        UserDetails userDetails = userDetailsService.loadUserByUsername("Abhay");
        assertNotNull(userDetails);
    }
}

/*
@SpringBootTest
public class CustomUserServiceImplTests2 {

    @Autowired
    private CustomUserServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void loadUserByUsernameTest() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userRepository.findByUserName("Abhay").getUserName());
        assertNotNull(userDetails);
    }
}
*/