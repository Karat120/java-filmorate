package ru.yandex.practicum.filmorate.film.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.MpaRatingNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.MpaRating;
import ru.yandex.practicum.filmorate.film.domain.repository.MpaStorage;

import java.util.List;

@Service
@AllArgsConstructor
public class MpaUseCase {
    private final MpaStorage mpaStorage;

    public MpaRating add(MpaRating mpa) {
        mpaStorage.add(mpa);
        return mpa;
    }

    public MpaRating getById(Long id) {
        return mpaStorage.getById(id).orElseThrow(MpaRatingNotFoundException::new);
    }

    public List<MpaRating> getAll() {
        return mpaStorage.getAll();
    }

    public boolean existsById(Long id) {
        return mpaStorage.getById(id).isPresent();
    }

    public MpaRating update(MpaRating mpa) {
        mpaStorage.getById(mpa.getId()).orElseThrow(MpaRatingNotFoundException::new);
        mpaStorage.update(mpa);
        return mpa;
    }

    public void delete(Long id) {
        mpaStorage.getById(id).orElseThrow(MpaRatingNotFoundException::new);
        mpaStorage.delete(id);
    }
}
