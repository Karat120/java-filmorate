package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserStorage userStorage;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setId(1L);

        user2 = new User();
        user2.setId(2L);
    }

    @Test
    void createUser_shouldDelegateToStorage() {
        when(userStorage.addUser(user1)).thenReturn(user1);

        var created = userService.createUser(user1);

        assertEquals(1L, user1.getId());
        assertEquals(user1, created);

        verify(userStorage).addUser(user1);
    }

    @Test
    void getUserById_shouldReturnUserIfExists() {
        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));

        var result = userService.getUserById(1L);

        assertEquals(user1, result);
        verify(userStorage).getUserById(1L);
    }

    @Test
    void getUserById_shouldThrowIfNotFound() {
        when(userStorage.getUserById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void updateUser_shouldDelegateToStorage() {
        when(userStorage.updateUser(user1)).thenReturn(user1);

        var updated = userService.updateUser(user1);

        assertEquals(user1, updated);
        verify(userStorage).updateUser(user1);
    }

    @Test
    void deleteUser_shouldDelegateToStorage() {
        user1.setId(1L);

        userService.deleteUser(user1);

        verify(userStorage).deleteUser(1L);
    }

    @Test
    void becomeFriends_shouldUpdateBothUsers() {
        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getUserById(2L)).thenReturn(Optional.of(user2));
        when(userStorage.updateUser(any())).thenAnswer(inv -> inv.getArgument(0));

        userService.becomeFriends(1L, 2L);

        assertTrue(user1.getFriends().contains(2L));
        assertTrue(user2.getFriends().contains(1L));

        verify(userStorage, times(2)).updateUser(any());
    }

    @Test
    void becomeFriends_shouldThrowIfAlreadyFriends() {
        user1.addFriend(2L);
        user2.addFriend(1L);

        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getUserById(2L)).thenReturn(Optional.of(user2));

        assertThrows(IllegalStateException.class, () -> userService.becomeFriends(1L, 2L));
    }

    @Test
    void breakFriendship_shouldRemoveFriends() {
        user1.addFriend(2L);
        user2.addFriend(1L);

        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getUserById(2L)).thenReturn(Optional.of(user2));

        userService.breakFriendship(1L, 2L);

        assertFalse(user1.getFriends().contains(2L));
        assertFalse(user2.getFriends().contains(1L));
    }

    @Test
    void breakFriendship_shouldThrowIfNotFriends() {
        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getUserById(2L)).thenReturn(Optional.of(user2));

        assertThrows(IllegalStateException.class, () -> userService.breakFriendship(1L, 2L));
    }

    @Test
    void getMutualFriends_shouldReturnIntersection() {
        var user3 = new User();
        user3.setId(3L);

        user1.addFriend(3L);
        user2.addFriend(3L);

        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getUserById(2L)).thenReturn(Optional.of(user2));
        when(userStorage.getUserById(3L)).thenReturn(Optional.of(user3));

        var mutual = userService.getMutualFriends(1L, 2L);

        assertEquals(List.of(user3), mutual);
    }

    @Test
    void isFriends_shouldReturnTrueIfMutual() {
        user1.addFriend(2L);
        user2.addFriend(1L);

        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getUserById(2L)).thenReturn(Optional.of(user2));

        assertTrue(userService.isFriends(1L, 2L));
    }

    @Test
    void isFriends_shouldReturnFalseIfOneSided() {
        user1.addFriend(2L);

        when(userStorage.getUserById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getUserById(2L)).thenReturn(Optional.of(user2));

        assertFalse(userService.isFriends(1L, 2L));
    }
}
