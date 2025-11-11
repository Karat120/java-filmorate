package ru.yandex.practicum.filmorate.user.domain.exception;

public class UserHasNotLikedException extends RuntimeException {
    public UserHasNotLikedException(String message) {
        super(message);
    }

    public UserHasNotLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}