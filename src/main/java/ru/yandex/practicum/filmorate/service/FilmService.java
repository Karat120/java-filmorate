package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

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
        film.likeBy(userId);
    }

    private void unlikeInternal(Film film, Long userId) {
        film.unlikeBy(userId);
    }
}
