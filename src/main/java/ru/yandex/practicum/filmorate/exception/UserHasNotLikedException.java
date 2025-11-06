package ru.yandex.practicum.filmorate.exception;

public class UserHasNotLikedException extends RuntimeException {
    public UserHasNotLikedException(String message) {
        super(message);
    }

    public UserHasNotLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}