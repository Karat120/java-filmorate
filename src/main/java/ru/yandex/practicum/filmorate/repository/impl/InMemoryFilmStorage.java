package ru.yandex.practicum.filmorate.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmStorage;
import ru.yandex.practicum.filmorate.util.IdGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private final IdGenerator<Long> idGenerator;

    @Override
    public Film add(Film film) {
        film.setId(idGenerator.generate());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> getById(Long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public List<Film> getTopNFilmsByLikes(int n) {
        return films.values().stream()
                .sorted(Comparator.comparingInt(Film::countLikes).reversed())
                .limit(n)
                .toList();
    }

    @Override
    public Film update(Film film) {
        if (film.getId() == null || !films.containsKey(film.getId())) {
            throw new UserNotFoundException();
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void delete(Long id) {
        if (!films.containsKey(id)) {
            throw new UserNotFoundException();
        }
        films.remove(id);
    }
}
