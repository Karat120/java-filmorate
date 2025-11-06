package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public User create(@Valid @RequestBody User user) {
        userService.create(user);

        log.info("User created: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        var users = userService.getAll();

        log.debug("Retrieving all users (total={})", users.size());
        return users;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        userService.update(user);

        log.info("User updated: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);

        log.info("User deleted: id={}", id);
    }
}
