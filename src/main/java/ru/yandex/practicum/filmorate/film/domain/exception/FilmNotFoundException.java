package ru.yandex.practicum.filmorate.film.domain.exception;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException() {
        super("Film not found");
    }

    public FilmNotFoundException(String message) {
        super(message);
    }

    public FilmNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
