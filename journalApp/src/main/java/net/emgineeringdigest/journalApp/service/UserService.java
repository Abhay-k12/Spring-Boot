package net.emgineeringdigest.journalApp.service;

import net.emgineeringdigest.journalApp.entity.User;
import net.emgineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void saveEntry(User entry){
        userRepository.save(entry);
    }

    public List<User> getAll() {
        return userRepository.findAll();
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
}
