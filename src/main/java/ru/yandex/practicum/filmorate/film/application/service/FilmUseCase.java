package ru.yandex.practicum.filmorate.film.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.domain.repository.FilmStorage;
import ru.yandex.practicum.filmorate.film.domain.service.FilmService;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmUseCase {
    private final FilmStorage filmStorage;
    private FilmService filmService;

    public void create(Film film) {
        filmStorage.add(film);
    }

    public Film getById(Long id) {
        return filmStorage.getById(id).orElseThrow(FilmNotFoundException::new);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film update(Film film) {
        filmStorage.update(film);
        return film;
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

        filmService.like(film, likedById);
    }

    public void unlike(Long filmId, Long userId) {
        var film = filmStorage.getById(filmId).orElseThrow(FilmNotFoundException::new);

        filmService.unlike(film, userId);
    }
}
