package ru.yandex.practicum.filmorate.film.presentation.rest.dto.film;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.yandex.practicum.filmorate.film.presentation.annotation.NotBeforeCinemaBirthday;
import ru.yandex.practicum.filmorate.shared.presentation.annotation.PositiveDuration;
import ru.yandex.practicum.filmorate.shared.presentation.service.jackson.DurationMinutesDeserializer;
import ru.yandex.practicum.filmorate.shared.presentation.service.jackson.DurationMinutesSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

public record UpdateFilmRequest(
        Long id,
        @NotBlank(message = "The film name cannot be empty")
        String name,
        @Size(max = 200, message = "The film description must not exceed 200 characters")
        String description,
        @NotBeforeCinemaBirthday
        LocalDate releaseDate,
        @JsonSerialize(using = DurationMinutesSerializer.class)
        @JsonDeserialize(using = DurationMinutesDeserializer.class)
        @PositiveDuration
        Duration durationMinutes,
        Long mpaRating,
        Set<Long> userLikes,
        Set<Long> genres
) {
}
