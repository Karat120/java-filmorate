package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.filmorate.user.domain.model.User;
import ru.yandex.practicum.filmorate.user.infrastructure.repository.h2.impl.H2UserStorage;
import ru.yandex.practicum.filmorate.user.infrastructure.repository.h2.mapper.UserMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({H2UserStorage.class, UserMapper.class})
@ActiveProfiles("test")
class H2UserStorageTest {

    @Autowired
    private H2UserStorage userStorage;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void clearDatabase() {
        jdbc.update("DELETE FROM friendship");
        jdbc.update("DELETE FROM user_account");
    }

    @Test
    @DisplayName("Add and get user by ID")
    void addAndGetUserById() {
        User user = new User();
        user.setEmail("neo@matrix.io");
        user.setLogin("neo");
        user.setName("Neo");
        user.setBirthday(LocalDate.of(1970, 3, 11));

        User saved = userStorage.add(user);
        Optional<User> found = userStorage.getById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("neo@matrix.io");
        assertThat(found.get().getLogin()).isEqualTo("neo");
    }

    @Test
    @DisplayName("Update user information")
    void updateUser() {
        User user = new User();
        user.setEmail("smith@matrix.io");
        user.setLogin("smith");
        user.setName("Agent Smith");
        user.setBirthday(LocalDate.of(1975, 1, 1));

        User saved = userStorage.add(user);

        saved.setEmail("smith@zion.net");
        saved.setName("Updated Agent");
        userStorage.update(saved);

        Optional<User> found = userStorage.getById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("smith@zion.net");
        assertThat(found.get().getName()).isEqualTo("Updated Agent");
    }

    @Test
    @DisplayName("Delete user by ID")
    void deleteUser() {
        User user = new User();
        user.setEmail("trinity@matrix.io");
        user.setLogin("trinity");
        user.setName("Trinity");
        user.setBirthday(LocalDate.of(1980, 5, 1));

        User saved = userStorage.add(user);
        userStorage.delete(saved.getId());

        Optional<User> found = userStorage.getById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        User user1 = new User();
        user1.setEmail("a@a.com");
        user1.setLogin("a");
        user1.setName("User A");
        user1.setBirthday(LocalDate.of(1990, 1, 1));

        User user2 = new User();
        user2.setEmail("b@b.com");
        user2.setLogin("b");
        user2.setName("User B");
        user2.setBirthday(LocalDate.of(1991, 2, 2));

        userStorage.add(user1);
        userStorage.add(user2);

        List<User> all = userStorage.getAll();
        assertThat(all)
                .hasSize(2)
                .extracting(User::getLogin)
                .containsExactlyInAnyOrder("a", "b");
    }

    @Test
    @DisplayName("Check if user exists by ID")
    void existsById() {
        User user = new User();
        user.setEmail("exist@user.com");
        user.setLogin("exists");
        user.setName("Exists");
        user.setBirthday(LocalDate.of(1995, 5, 5));

        User saved = userStorage.add(user);

        assertThat(userStorage.existsById(saved.getId())).isTrue();
        assertThat(userStorage.existsById(999L)).isFalse();
    }

    @Test
    @DisplayName("Load user friends from friendship table")
    void loadUserFriends() {
        // Create two users
        User user1 = new User();
        user1.setEmail("a@a.com");
        user1.setLogin("a");
        user1.setName("A");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User saved1 = userStorage.add(user1);

        User user2 = new User();
        user2.setEmail("b@b.com");
        user2.setLogin("b");
        user2.setName("B");
        user2.setBirthday(LocalDate.of(1991, 2, 2));
        User saved2 = userStorage.add(user2);

        saved1.addFriend(saved2.getId());

        jdbc.update("INSERT INTO friendship(user_id, friend_id) VALUES (?, ?)", saved1.getId(), saved2.getId());

        Optional<User> found = userStorage.getById(saved1.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getFriends()).containsExactly(saved2.getId());
    }

    @Test
    @DisplayName("Get all users by given IDs")
    void getAllByIds() {
        User user1 = new User();
        user1.setEmail("a@a.com");
        user1.setLogin("a");
        user1.setName("A");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        User saved1 = userStorage.add(user1);

        User user2 = new User();
        user2.setEmail("b@b.com");
        user2.setLogin("b");
        user2.setName("B");
        user2.setBirthday(LocalDate.of(1991, 2, 2));
        User saved2 = userStorage.add(user2);

        List<User> found = userStorage.getAllByIds(List.of(saved1.getId(), saved2.getId()));

        assertThat(found)
                .hasSize(2)
                .extracting(User::getLogin)
                .containsExactlyInAnyOrder("a", "b");
    }
}
