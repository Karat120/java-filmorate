package ru.yandex.practicum.filmorate.film.domain.exception;

public class MpaRatingNotFoundException extends RuntimeException {
    public MpaRatingNotFoundException() {
        super("Mpa rating not found");
    }

    public MpaRatingNotFoundException(String message) {
        super(message);
    }

    public MpaRatingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
