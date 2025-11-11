package ru.yandex.practicum.filmorate.film.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserAlreadyLikedException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserHasNotLikedException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.user.application.service.UserService;
import ru.yandex.practicum.filmorate.film.domain.repository.FilmStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    public Film create(Film film) {
        return filmStorage.add(film);
    }

    public Film getById(Long id) {
        return filmStorage.getById(id).orElseThrow(FilmNotFoundException::new);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void delete(Film film) {
        deleteById(film.getId());
    }

    public void deleteById(Long id) {
        filmStorage.delete(id);
    }

    public List<Film> getTopNFilmsByLikes(int n) {
        return filmStorage.getTopNFilmsByLikes(n);
    }

    public void like(Long filmId, Long likedById) {
        var film = filmStorage.getById(filmId).orElseThrow(FilmNotFoundException::new);

        likeInternal(film, likedById);
    }

    public void unlike(Long filmId, Long userId) {
        var film = filmStorage.getById(filmId).orElseThrow(FilmNotFoundException::new);

        unlikeInternal(film, userId);
    }

    private void likeInternal(Film film, Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (film.isLikedBy(userId)) {
            throw new UserAlreadyLikedException("User with ID " + userId + " has already liked this film.");
        }
        film.likeBy(userId);
    }

    private void unlikeInternal(Film film, Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException();
        }
        if (!film.isLikedBy(userId)) {
            throw new UserHasNotLikedException("User with ID " + userId + " has not liked this film.");
        }
        film.unlikeBy(userId);
    }
}
