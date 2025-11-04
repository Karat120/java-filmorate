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
    private static final int TOP_FILMS_COUNT = 10;

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
        filmStorage.delete(film.getId());
    }

    public List<Film> getTopTenFilmsByLikes() {
        return filmStorage.getTopNFilmsByLikes(TOP_FILMS_COUNT);
    }
}
