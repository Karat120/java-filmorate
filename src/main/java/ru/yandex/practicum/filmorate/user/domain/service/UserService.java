package ru.yandex.practicum.filmorate.user.domain.service;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.user.domain.exception.FriendshipViolationException;
import ru.yandex.practicum.filmorate.user.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    public void becomeFriends(User firstUser, User secondUser) {
        if (isFriends(firstUser, secondUser)) {
            throw new FriendshipViolationException("Users is already friends");
        }

        firstUser.addFriend(secondUser.getId());
        secondUser.addFriend(firstUser.getId());
    }

    public void breakFriendship(User firstUser, User secondUser) {
        if (!isFriends(firstUser, secondUser)) {
            return;
        }

        firstUser.removeFriend(secondUser.getId());
        secondUser.removeFriend(firstUser.getId());
    }

    public List<Long> getMutualFriends(User firstUser, User secondUser) {
        Set<Long> intersection = Sets.intersection(firstUser.getFriends(), secondUser.getFriends());
        return new ArrayList<>(intersection);
    }

    public boolean isFriends(User firstUser, User secondUser) {
        return firstUser.hasFriend(secondUser.getId())
               && secondUser.hasFriend(firstUser.getId());
    }

    public User.FriendshipStatus getFriendshipStateWith(User firstUser, User secondUser) {
        if (firstUser.hasFriend(secondUser.getId()) ^ secondUser.hasFriend(firstUser.getId())) {
            return User.FriendshipStatus.PENDING;
        } else if (firstUser.hasFriend(secondUser.getId()) && secondUser.hasFriend(firstUser.getId())) {
            return User.FriendshipStatus.CONFIRMED;
        }
        return User.FriendshipStatus.NOT_FRIENDS;
    }
}
