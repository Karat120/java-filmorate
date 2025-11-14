package ru.yandex.practicum.filmorate.film.domain.repository;

import ru.yandex.practicum.filmorate.film.domain.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {
    void add(Mpa mpa);

    Optional<Mpa> getById(Long id);

    List<Mpa> getAll();

    void update(Mpa mpa);

    void delete(Long id);
}
