package net.emgineeringdigest.journalApp.service;

import net.emgineeringdigest.journalApp.entity.User;
import net.emgineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
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


    public void saveEntry(User user){
        userRepository.save(user);
    }

    public void saveNewEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
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
}
