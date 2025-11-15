package ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre;

public record UpdateGenreRequest(
        Long id,
        String name
) {
}
