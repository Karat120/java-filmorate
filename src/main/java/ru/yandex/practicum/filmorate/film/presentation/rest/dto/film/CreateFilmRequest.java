package ru.yandex.practicum.filmorate.film.presentation.rest.dto.film;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.yandex.practicum.filmorate.film.presentation.annotation.NotBeforeCinemaBirthday;
import ru.yandex.practicum.filmorate.shared.presentation.annotation.PositiveDuration;
import ru.yandex.practicum.filmorate.shared.presentation.service.jackson.DurationMinutesDeserializer;
import ru.yandex.practicum.filmorate.shared.presentation.service.jackson.DurationMinutesSerializer;

import java.util.Set;

public record CreateFilmRequest(
        @NotBlank(message = "The film name cannot be empty")
        String name,
        @Size(max = 200, message = "The film description must not exceed 200 characters")
        String description,
        @NotBeforeCinemaBirthday
        String releaseDate,
        @JsonSerialize(using = DurationMinutesSerializer.class)
        @JsonDeserialize(using = DurationMinutesDeserializer.class)
        @PositiveDuration
        Long durationMinutes,
        Long mpaRating,
        Set<Long> genres
) {
}
