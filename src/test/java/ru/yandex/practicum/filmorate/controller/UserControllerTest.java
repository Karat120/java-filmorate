package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void createUser_shouldAssignIdAndStoreUser() {
        User user = new User();
        user.setName("Alice");

        User created = userController.createUser(user);

        assertThat(created.getId()).isOne();
        assertThat(created.getName()).isEqualTo("Alice");
        assertThat(userController.getAllUsers()).containsExactly(created);
    }

    @Test
    void getAllUsers_shouldReturnAllCreatedUsers() {
        User u1 = new User();
        u1.setName("Bob");
        User u2 = new User();
        u2.setName("Charlie");

        userController.createUser(u1);
        userController.createUser(u2);

        List<User> users = userController.getAllUsers();

        assertThat(users).hasSize(2);
        assertThat(users)
                .extracting(User::getName)
                .containsExactlyInAnyOrder("Bob", "Charlie");
    }

    @Test
    void updateTask_shouldReplaceExistingUser() {
        User user = new User();
        user.setName("Old name");
        User created = userController.createUser(user);

        User updated = new User();
        updated.setId(created.getId());
        updated.setName("New name");

        User result = userController.updateUser(updated);

        assertThat(result.getName()).isEqualTo("New name");
        assertThat(userController.getAllUsers()).containsExactly(result);
    }

    @Test
    void updateTask_shouldThrowIfUserNotExists() {
        User ghost = new User();
        ghost.setId(999L);
        ghost.setName("Ghost user");

        assertThatThrownBy(() -> userController.updateUser(ghost))
                .isInstanceOf(UserNotFoundException.class);
    }
}
