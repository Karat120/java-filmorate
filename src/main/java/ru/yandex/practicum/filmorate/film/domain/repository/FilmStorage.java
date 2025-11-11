package ru.yandex.practicum.filmorate.film.domain.repository;

import ru.yandex.practicum.filmorate.film.domain.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film add(Film film);

    Optional<Film> getById(Long id);

    List<Film> getAll();

    List<Film> getTopNFilmsByLikes(int n);

    Film update(Film film);

    void delete(Long id);
}
