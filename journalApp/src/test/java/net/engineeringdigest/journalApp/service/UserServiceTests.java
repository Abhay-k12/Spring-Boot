package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest   //So that Spring will create component and beans (which was previously created by @SpringBootApplication annotation);
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach  //Similarly @AfterEach
    @Test
    public void setup() {
        System.out.println("This will run before running each individual test.");
        /*
            Example: This will run before running findUserByUsernameTest(), then will run again before
            findUserByUsernameTest(String) and then again before test2(int a, int b, int expected);
         */
    }

    @BeforeAll  //Similarly we have @AfterAll
    public static void setup2(){
        System.out.println("Mai to Papa hu, iss Duniya ka papa, sare test se pehle m chalunga");
    }

    /*
        Example we have created a csv file with @BeforeAll and then after all test executed, we'll delete that file
        with @AfterAll annotation.
     */

    @Disabled    // when we run whole class, then this one will be skipped.
    @Test
    public void findUserByUsernameTest() {
        assertEquals(4,3+1);
        assertNotNull(userRepository.findByUserName("Abhay"));
        assertFalse(userRepository.findByUserName("Anshika").getJournalEntries().isEmpty());
        assertNull(userRepository.findByUserName("Ram"));
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "Abhay",
            "Anshika",
            "Sunita"
    })
    public void findUserByUserNameTest(String user) {
        // If the test will fail for some name, then the given message will be shown into the terminal.
        assertNotNull(userRepository.findByUserName(user),"User that doesn't found in Db:" + user);
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,1" ,
            "2,2,2",
            "11,11,11"
    })
    public void test2(int a, int b, int expected) {
        assertTrue(a==b && b==expected, "The values that are causing error are: " +a+" " +b+" "+expected);
    }
}
