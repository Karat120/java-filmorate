package ru.yandex.practicum.filmorate.user.domain.exception;

public class FriendshipViolationException extends RuntimeException {
    public FriendshipViolationException(String message) {
        super(message);
    }

    public FriendshipViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
