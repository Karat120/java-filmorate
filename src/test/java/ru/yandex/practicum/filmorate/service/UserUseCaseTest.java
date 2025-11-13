package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.user.domain.exception.FriendshipViolationException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.user.domain.model.User;
import ru.yandex.practicum.filmorate.user.application.service.UserUseCase;
import ru.yandex.practicum.filmorate.user.domain.repository.UserStorage;

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
class UserUseCaseTest {

    @Mock
    private UserStorage userStorage;

    @InjectMocks
    private UserUseCase userUseCase;

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
    void create_shouldDelegateToStorage() {
        userUseCase.create(user1);

        assertEquals(1L, user1.getId());
        verify(userStorage).add(user1);
    }

    @Test
    void getById_shouldReturnUserIfExists() {
        when(userStorage.getById(1L)).thenReturn(Optional.of(user1));

        var result = userUseCase.getById(1L);

        assertEquals(user1, result);
        verify(userStorage).getById(1L);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(userStorage.getById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userUseCase.getById(99L));
    }

    @Test
    void update_shouldDelegateToStorage() {
        userUseCase.update(user1);

        verify(userStorage).update(user1);
    }

    @Test
    void delete_shouldDelegateToStorage() {
        user1.setId(1L);

        userUseCase.delete(user1);

        verify(userStorage).delete(1L);
    }

    @Test
    void becomeFriends_shouldUpdateBothUsers() {
        when(userStorage.getById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getById(2L)).thenReturn(Optional.of(user2));

        userUseCase.becomeFriends(1L, 2L);

        assertTrue(user1.getFriends().contains(2L));
        assertTrue(user2.getFriends().contains(1L));

        verify(userStorage, times(2)).update(any());
    }

    @Test
    void becomeFriends_shouldThrowIfAlreadyFriends() {
        user1.addFriend(2L);
        user2.addFriend(1L);

        when(userStorage.getById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getById(2L)).thenReturn(Optional.of(user2));

        assertThrows(FriendshipViolationException.class, () -> userUseCase.becomeFriends(1L, 2L));
    }

    @Test
    void breakFriendship_shouldRemoveFriends() {
        user1.addFriend(2L);
        user2.addFriend(1L);

        when(userStorage.getById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getById(2L)).thenReturn(Optional.of(user2));

        userUseCase.breakFriendship(1L, 2L);

        assertFalse(user1.getFriends().contains(2L));
        assertFalse(user2.getFriends().contains(1L));
    }

    @Test
    void getMutualFriends_shouldReturnIntersection() {
        var user3 = new User();
        user3.setId(3L);

        user1.addFriend(3L);
        user2.addFriend(3L);

        when(userStorage.getById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getById(2L)).thenReturn(Optional.of(user2));
        when(userStorage.getById(3L)).thenReturn(Optional.of(user3));

        var mutual = userUseCase.getMutualFriends(1L, 2L);

        assertEquals(List.of(user3), mutual);
    }

    @Test
    void isFriends_shouldReturnTrueIfMutual() {
        user1.addFriend(2L);
        user2.addFriend(1L);

        when(userStorage.getById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getById(2L)).thenReturn(Optional.of(user2));

        assertTrue(userUseCase.isFriends(1L, 2L));
    }

    @Test
    void isFriends_shouldReturnFalseIfOneSided() {
        user1.addFriend(2L);

        when(userStorage.getById(1L)).thenReturn(Optional.of(user1));
        when(userStorage.getById(2L)).thenReturn(Optional.of(user2));

        assertFalse(userUseCase.isFriends(1L, 2L));
    }
}
