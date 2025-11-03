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

    public Film createFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film getFilmById(Long id) {
        return filmStorage.getFilmById(id).orElseThrow(FilmNotFoundException::new);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void deleteFilm(Film film) {
        filmStorage.deleteFilm(film.getId());
    }

    public List<Film> getTopTenFilmsByLikes() {
        return filmStorage.getTopNFilmsByLikes(TOP_FILMS_COUNT);
    }
}
