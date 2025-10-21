package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.util.DurationMinutesDeserializer;
import ru.yandex.practicum.filmorate.util.DurationMinutesSerializer;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {

    private Long id;

    @NotBlank(message = "The film name cannot be empty")
    private String name;

    @Size(max = 200, message = "The film description must not exceed 200 characters")
    private String description;

    private LocalDate releaseDate;

    @JsonSerialize(using = DurationMinutesSerializer.class)
    @JsonDeserialize(using = DurationMinutesDeserializer.class)
    private Duration duration;

    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    public void setReleaseDate(LocalDate releaseDate) {
        if (releaseDate != null && releaseDate.isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("The release date cannot be earlier than December 28, 1895");
        }
        this.releaseDate = releaseDate;
    }

    public void setDuration(Duration duration) {
        if (duration == null || duration.isNegative() || duration.isZero()) {
            throw new ValidationException("Film duration must be positive");
        }
        this.duration = duration;
    }
}
