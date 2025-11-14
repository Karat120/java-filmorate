package ru.yandex.practicum.filmorate.film.domain.exception;

public class MpaNotFoundException extends RuntimeException {
    public MpaNotFoundException() {
        super("Mpa rating not found");
    }

    public MpaNotFoundException(String message) {
        super(message);
    }

    public MpaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
