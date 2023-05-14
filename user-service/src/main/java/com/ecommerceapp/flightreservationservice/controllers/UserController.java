package com.ecommerceapp.flightreservationservice.controllers;

import com.ecommerceapp.flightreservationservice.models.User;
import com.ecommerceapp.flightreservationservice.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path="/getAllUsers")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable("id") String id) {
        Optional<User> u = userRepository.findById(new ObjectId(id));
        if (u.isPresent()) {
            return u.get();
        }
        return null;
    }

    @PostMapping(path="/addUser", consumes={"application/JSON"}, produces="application/json")
    public User createUser(@RequestBody User u) {
        return userRepository.save(u);
    }

    @DeleteMapping(path="/deleteAllUsers")
    public void deleteUsers() {
        userRepository.deleteAll();
    }

    @DeleteMapping(path="/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userRepository.deleteById(new ObjectId(id));
    }

}