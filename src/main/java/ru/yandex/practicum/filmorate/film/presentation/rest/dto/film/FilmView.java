package ru.yandex.practicum.filmorate.film.presentation.rest.dto.film;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaRatingView;
import ru.yandex.practicum.filmorate.shared.presentation.service.jackson.DurationMinutesDeserializer;
import ru.yandex.practicum.filmorate.shared.presentation.service.jackson.DurationMinutesSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

public record FilmView(
        Long id,
        String name,
        String description,
        LocalDate releaseDate,
        @JsonSerialize(using = DurationMinutesSerializer.class)
        @JsonDeserialize(using = DurationMinutesDeserializer.class)
        Duration duration,
        MpaRatingView mpaRating,
        Long likeCount,
        Set<GenreView> genres
) {
}
