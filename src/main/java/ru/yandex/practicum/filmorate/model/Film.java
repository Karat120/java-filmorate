package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.NotBeforeCinemaBirthday;
import ru.yandex.practicum.filmorate.annotation.PositiveDuration;
import ru.yandex.practicum.filmorate.util.DurationMinutesDeserializer;
import ru.yandex.practicum.filmorate.util.DurationMinutesSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private Long id;

    @NotBlank(message = "The film name cannot be empty")
    private String name;

    @Size(max = 200, message = "The film description must not exceed 200 characters")
    private String description;

    @NotBeforeCinemaBirthday
    private LocalDate releaseDate;

    @JsonSerialize(using = DurationMinutesSerializer.class)
    @JsonDeserialize(using = DurationMinutesDeserializer.class)
    @PositiveDuration
    private Duration duration;

    private Set<Long> userLikes = new HashSet<>();

    public void likeBy(Long userId) {
        if (isLikedBy(userId)) {
            throw new IllegalArgumentException(
                    "User cannot like film more than one time");
        }
        userLikes.add(userId);
    }

    public void unlikeBy(Long userId) {
        if (!isLikedBy(userId)) {
            throw new IllegalArgumentException(
                    "Cannot unlike a film that was not liked");
        }
        userLikes.remove(userId);
    }

    public boolean isLikedBy(Long userId) {
        return userLikes.contains(userId);
    }

    public int countLikes() {
        return userLikes.size();
    }
}
