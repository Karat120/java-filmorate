package ru.yandex.practicum.filmorate.exception;

public class FriendshipViolationException extends RuntimeException {
    public FriendshipViolationException(String message) {
        super(message);
    }

    public FriendshipViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
