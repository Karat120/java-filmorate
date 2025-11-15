package ru.yandex.practicum.filmorate.film.domain.repository;

import ru.yandex.practicum.filmorate.film.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    Optional<Genre> getById(Long id);

    List<Genre> getAll();

    List<Genre> getAllByIds(List<Long> ids);
}
