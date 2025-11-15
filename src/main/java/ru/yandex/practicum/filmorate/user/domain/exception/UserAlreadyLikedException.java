package ru.yandex.practicum.filmorate.user.domain.exception;

public class UserAlreadyLikedException extends RuntimeException {
    public UserAlreadyLikedException(String message) {
        super(message);
    }

    public UserAlreadyLikedException(String message, Throwable cause) {
        super(message, cause);
    }
}
