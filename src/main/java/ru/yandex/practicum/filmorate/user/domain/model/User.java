package ru.yandex.practicum.filmorate.user.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class User {
    public enum FriendshipStatus {
        PENDING,
        CONFIRMED,
        NOT_FRIENDS
    }

    private Long id;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must contain '@' and be valid")
    private String email;

    @NotBlank(message = "Login cannot be empty")
    @Pattern(regexp = "\\S+", message = "Login must not contain spaces")
    private String login;

    private String name;

    @PastOrPresent(message = "Birthday cannot be in the future")
    private LocalDate birthday;

    private Set<Long> friends = new HashSet<>();

    public void setLogin(String login) {
        this.login = login;
        if (this.name == null || this.name.isBlank()) {
            this.name = login;
        }
    }

    public boolean hasFriend(Long otherUserId) {
        return friends.contains(otherUserId);
    }

    public void addFriend(Long otherUserId) {
        if (Objects.equals(otherUserId, this.id)) {
            throw new IllegalArgumentException("User cannot be a friend to himself");
        }
        if (hasFriend(otherUserId)) {
            throw new IllegalArgumentException(
                    "The user with id=%d is already a friend of the user with id=%d".formatted(otherUserId, this.id));
        }
        friends.add(otherUserId);
    }

    public void removeFriend(Long otherUserId) {
        if (Objects.equals(otherUserId, this.id)) {
            throw new IllegalArgumentException("User cannot remove himself from the friends list");
        }
        if (!hasFriend(otherUserId)) {
            throw new IllegalArgumentException(
                    "The user with id=%d is not a friend of the user with id=%d".formatted(otherUserId, this.id));
        }
        friends.remove(otherUserId);
    }
}