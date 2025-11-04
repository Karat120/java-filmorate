package ru.yandex.practicum.filmorate.service;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(User user) {
        return userStorage.add(user);
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
        return userStorage.update(user);
    }

    public void delete(User user) {
        deleteById(user.getId());
    }

    public void deleteById(Long id) {
        userStorage.delete(id);
    }

    public void becomeFriends(Long firstUserId, Long secondUserId) {
        var firstUser = userStorage.getById(firstUserId).orElseThrow(UserNotFoundException::new);
        var secondUser = userStorage.getById(secondUserId).orElseThrow(UserNotFoundException::new);

        becomeFriendsInternal(firstUser, secondUser);
    }

    public void breakFriendship(Long firstUserId, Long secondUserId) {
        var firstUser = userStorage.getById(firstUserId).orElseThrow(UserNotFoundException::new);
        var secondUser = userStorage.getById(secondUserId).orElseThrow(UserNotFoundException::new);

        breakFriendshipInternal(firstUser, secondUser);
    }

    public List<User> getMutualFriends(Long firstUserId, Long secondUserId) {
        var firstUser = userStorage.getById(firstUserId).orElseThrow(UserNotFoundException::new);
        var secondUser = userStorage.getById(secondUserId).orElseThrow(UserNotFoundException::new);

        return getMutualFriendsInternal(firstUser, secondUser);
    }

    public boolean isFriends(Long firstUserId, Long secondUserId) {
        var firstUser = userStorage.getById(firstUserId).orElseThrow(UserNotFoundException::new);
        var secondUser = userStorage.getById(secondUserId).orElseThrow(UserNotFoundException::new);

        return isFriendsInternal(firstUser, secondUser);
    }

    private void becomeFriendsInternal(User firstUser, User secondUser) {
        if (isFriendsInternal(firstUser, secondUser)) {
            throw new IllegalStateException("Users is already friends");
        }

        firstUser.addFriend(secondUser.getId());
        secondUser.addFriend(firstUser.getId());

        userStorage.update(firstUser);
        userStorage.update(secondUser);
    }

    private void breakFriendshipInternal(User firstUser, User secondUser) {
        if (!isFriendsInternal(firstUser, secondUser)) {
            throw new IllegalStateException("Users must be friends to break friendship");
        }

        firstUser.removeFriend(secondUser.getId());
        secondUser.removeFriend(firstUser.getId());
    }

    private List<User> getMutualFriendsInternal(User firstUser, User secondUser) {
        return Sets.intersection(firstUser.getFriends(), secondUser.getFriends()).stream()
                .map(id -> userStorage.getById(id).orElseThrow(UserNotFoundException::new))
                .toList();
    }

    private boolean isFriendsInternal(User firstUser, User secondUser) {
        return firstUser.hasFriend(secondUser.getId())
                && secondUser.hasFriend(firstUser.getId());
    }
}
