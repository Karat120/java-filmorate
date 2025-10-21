package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private long lastGeneratedID = 0;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        user.setId(++lastGeneratedID);
        users.put(user.getId(), user);

        log.info("User created: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.debug("Retrieving all users (total={})", users.size());
        return new ArrayList<>(users.values());
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Attempt to update non-existent user id={}", user.getId());
            throw new UserNotFoundException();
        }
        users.put(user.getId(), user);
        log.info("User updated: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }
}
