package ru.yandex.practicum.filmorate.film.domain.repository;

import ru.yandex.practicum.filmorate.film.domain.model.MpaRating;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {
    void add(MpaRating mpaRating);

    Optional<MpaRating> getById(Long id);

    List<MpaRating> getAll();

    void update(MpaRating mpa);

    void delete(Long id);
}
