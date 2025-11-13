package ru.yandex.practicum.filmorate.film.domain.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.user.domain.exception.UserAlreadyLikedException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserHasNotLikedException;

@Service
public class FilmService {

    public void like(Film film, Long userId) {
        if (film.isLikedBy(userId)) {
            throw new UserAlreadyLikedException("User with ID " + userId + " has already liked this film.");
        }
        film.likeBy(userId);
    }

    public void unlike(Film film, Long userId) {
        if (!film.isLikedBy(userId)) {
            throw new UserHasNotLikedException("User with ID " + userId + " has not liked this film.");
        }
        film.unlikeBy(userId);
    }
}
