package ru.yandex.practicum.filmorate.film.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.user.application.service.UserService;
import ru.yandex.practicum.filmorate.user.domain.exception.UserAlreadyLikedException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserHasNotLikedException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserNotFoundException;

@Service
@AllArgsConstructor
public class FilmService {
    private final UserService userService;

    public void like(Film film, Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (film.isLikedBy(userId)) {
            throw new UserAlreadyLikedException("User with ID " + userId + " has already liked this film.");
        }
        film.likeBy(userId);
    }

    public void unlike(Film film, Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (!film.isLikedBy(userId)) {
            throw new UserHasNotLikedException("User with ID " + userId + " has not liked this film.");
        }
        film.unlikeBy(userId);
    }
}
