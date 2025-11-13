package ru.yandex.practicum.filmorate.film.presentation.rest.dto.film;

import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaRatingView;

import java.util.Set;

public record FilmView(
        Long id,
        String name,
        String description,
        String releaseDate,
        Long durationMinutes,
        MpaRatingView mpaRating,
        Long likeCount,
        Set<GenreView> genres
) {
}
