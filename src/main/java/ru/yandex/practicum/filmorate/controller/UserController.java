package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        userService.createUser(user);

        log.info("User created: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        var users = userService.getAllUsers();

        log.debug("Retrieving all users (total={})", users.size());
        return users;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user);

        log.info("User updated: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }
}
