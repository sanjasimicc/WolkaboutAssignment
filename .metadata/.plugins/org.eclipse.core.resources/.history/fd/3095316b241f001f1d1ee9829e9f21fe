package com.wolkabout.exam.api;

import com.wolkabout.exam.api.dto.UserCreationDetails;
import com.wolkabout.exam.api.dto.UserUpdateDetails;
import com.wolkabout.exam.repository.UserRepository;
import com.wolkabout.exam.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApi {

    private final UserRepository userRepository;

    @Autowired
    public UserApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(@RequestParam String searchParameters) {
        final List<User> users = userRepository.findByEmailContaining(searchParameters);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserCreationDetails details) {
        final User user = new User();
        user.setEmail(details.getEmail());
        user.setPassword(details.getPassword());
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody UserUpdateDetails details) {
        return userRepository.findById(id).map(user -> {
            user.setPassword(details.getPassword());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
