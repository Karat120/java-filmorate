package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;
import java.util.Optional;

public interface MpaStorage {
    MpaRating add(MpaRating mpaRating);

    Optional<MpaRating> getById(Long id);

    List<MpaRating> getAll();

    MpaRating update(MpaRating mpa);

    void delete(Long id);
}
