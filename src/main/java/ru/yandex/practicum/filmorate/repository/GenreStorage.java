package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    Genre add(Genre genre);

    Optional<Genre> getById(Long id);

    List<Genre> getAll();

    Genre update(Genre genre);

    void delete(Long id);
}
