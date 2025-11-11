package ru.yandex.practicum.filmorate.user.presentation.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.user.domain.model.User;
import ru.yandex.practicum.filmorate.user.application.service.UserService;

import java.util.List;

@RequestMapping("/users/{thisId}/friends")
@AllArgsConstructor
@RestController
public class FriendsController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll(@PathVariable Long thisId) {
        return userService.getFriends(thisId);
    }

    @GetMapping("/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long thisId, @PathVariable Long otherId) {
        return userService.getMutualFriends(thisId, otherId);
    }

    @PutMapping("/{otherId}")
    public void add(@PathVariable Long thisId, @PathVariable Long otherId) {
        userService.becomeFriends(thisId, otherId);
    }

    @DeleteMapping("/{otherId}")
    public void remove(@PathVariable Long thisId, @PathVariable Long otherId) {
        userService.breakFriendship(thisId, otherId);
    }
}
