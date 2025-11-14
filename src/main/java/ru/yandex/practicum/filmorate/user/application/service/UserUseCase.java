package ru.yandex.practicum.filmorate.user.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.user.domain.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.user.domain.model.User;
import ru.yandex.practicum.filmorate.user.domain.model.User.FriendshipStatus;
import ru.yandex.practicum.filmorate.user.domain.repository.UserStorage;
import ru.yandex.practicum.filmorate.user.domain.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserUseCase {
    private final UserStorage userStorage;
    private final UserService userService;

    public void create(User user) {
        userStorage.add(user);
    }

    public User getById(Long id) {
        return userStorage.getById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public List<User> getAllByIds(List<Long> ids) {
        return userStorage.getAllByIds(ids);
    }

    public User update(User user) {
        userStorage.update(user);
        return user;
    }

    public void delete(User user) {
        deleteById(user.getId());
    }

    public void deleteById(Long id) {
        userStorage.delete(id);
    }

    public User add(Long thisId, Long otherId) {
        var user = getById(thisId);
        getById(otherId);

        user.addFriend(otherId);

        update(user);

        return user;
    }

    public User remove(Long thisId, Long otherId) {
        var user = getById(thisId);
        getById(otherId);

        user.removeFriend(otherId);

        update(user);

        return user;
    }

    public void becomeFriends(Long firstUserId, Long secondUserId) {
        var firstUser = getById(firstUserId);
        var secondUser = getById(secondUserId);

        userService.becomeFriends(firstUser, secondUser);

        updateUsers(firstUser, secondUser);
    }

    public void breakFriendship(Long firstUserId, Long secondUserId) {
        var firstUser = getById(firstUserId);
        var secondUser = getById(secondUserId);

        userService.breakFriendship(firstUser, secondUser);

        updateUsers(firstUser, secondUser);
    }

    public List<User> getFriends(Long id) {
        List<Long> friendIds = new ArrayList<>(getById(id).getFriends());
        return getAllByIds(friendIds);
    }

    public List<User> getMutualFriends(Long firstUserId, Long secondUserId) {
        var firstUser = getById(firstUserId);
        var secondUser = getById(secondUserId);

        var ids = userService.getMutualFriends(firstUser, secondUser);

        return getAllByIds(ids);
    }

    public boolean isFriends(Long firstUserId, Long secondUserId) {
        var firstUser = getById(firstUserId);
        var secondUser = getById(secondUserId);

        return userService.isFriends(firstUser, secondUser);
    }

    public FriendshipStatus getFriendshipStateWith(Long firstUserId, Long secondUserId) {
        var firstUser = getById(firstUserId);
        var secondUser = getById(secondUserId);

        return userService.getFriendshipStateWith(firstUser, secondUser);
    }

    private void updateUsers(User... users) {
        for (User user : users) {
            userStorage.update(user);
        }
    }
}
