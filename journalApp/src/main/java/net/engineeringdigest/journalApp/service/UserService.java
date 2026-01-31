package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;  //This is just a reference variable, not instantiation, Instantiation is done internally by Spring.
    /*
        Question: How is an interface object created?
        Answer: Interfaces are not instantiated directly. Spring creates a runtime proxy class that implements the
        interface and injects it using Dependency Injection.

        The Spring stores that proxy class Object into the IOC container, and Spring injects the proxy object, NOT the interface itself.
        (Parent's reference can hold the object of the child class);
     */

    private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
    /*
        Use this when you are not using @Slf4j annotation.
        private static final Logger logger = LoggerFactory.getLogger(UserService.class);
     */

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public boolean saveNewEntry(User user) throws Exception{
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            log.error("{}Haahahahahhaha", e.toString());
            /*
                Use this when you are not using the @Slf4j annotation here and using a citsom object of logger class;
                logger.error("{} hahahahah", e.toString());
             */
            throw (e);
        }
    }

    public Optional<User> getById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteObjectById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    public List<User> getAll() {return userRepository.findAll();}

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(user);
    }
}
