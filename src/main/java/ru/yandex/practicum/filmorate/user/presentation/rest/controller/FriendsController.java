package ru.yandex.practicum.filmorate.user.presentation.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.user.domain.model.User;
import ru.yandex.practicum.filmorate.user.application.service.UserUseCase;

import java.util.List;

@RequestMapping("/users/{thisId}/friends")
@AllArgsConstructor
@RestController
public class FriendsController {
    private final UserUseCase userUseCase;

    @GetMapping
    public List<User> getAll(@PathVariable Long thisId) {
        return userUseCase.getFriends(thisId);
    }

    @GetMapping("/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long thisId, @PathVariable Long otherId) {
        return userUseCase.getMutualFriends(thisId, otherId);
    }

    @PutMapping("/{otherId}")
    public User add(@PathVariable Long thisId, @PathVariable Long otherId) {
        return userUseCase.add(thisId, otherId);
    }

    @DeleteMapping("/{otherId}")
    public User remove(@PathVariable Long thisId, @PathVariable Long otherId) {
        return userUseCase.remove(thisId, otherId);
    }
}
