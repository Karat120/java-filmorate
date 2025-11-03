package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film addFilm(Film film);
    Optional<Film> getFilmById(Long id);
    List<Film> getAllFilms();
    List<Film> getTopNFilmsByLikes(int n);
    Film updateFilm(Film film);
    void deleteFilm(Long id);
}
