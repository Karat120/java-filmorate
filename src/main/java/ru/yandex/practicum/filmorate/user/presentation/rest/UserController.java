package ru.yandex.practicum.filmorate.user.presentation.rest;

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
import ru.yandex.practicum.filmorate.user.domain.model.User;
import ru.yandex.practicum.filmorate.user.application.service.UserUseCase;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserUseCase userUseCase;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userUseCase.create(user);

        log.info("User created: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        var users = userUseCase.getAll();

        log.debug("Retrieving all users (total={})", users.size());
        return users;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        userUseCase.update(user);

        log.info("User updated: id={}, email={}, login={}", user.getId(), user.getEmail(), user.getLogin());
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userUseCase.deleteById(id);

        log.info("User deleted: id={}", id);
    }
}
