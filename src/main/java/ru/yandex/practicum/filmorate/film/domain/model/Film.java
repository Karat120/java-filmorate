package ru.yandex.practicum.filmorate.film.domain.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private Long id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private Duration duration;

    private Long mpa;

    private Set<Long> userLikes = new HashSet<>();

    private Set<Long> genres = new HashSet<>();

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
