package ru.yandex.practicum.filmorate.film.domain.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Film {

    private Long id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private Duration duration;

    private Long mpa;

    private List<Long> userLikes = new ArrayList<>();

    private List<Long> genres = new ArrayList<>();

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
