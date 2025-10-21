package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long lastGeneratedID = 0;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(lastGeneratedID++);
        films.put(film.getId(), film);

        log.info("Film created: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("Retrieving all films (total={})", films.size());
        return new ArrayList<>(films.values());
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Attempt to update non-existent film id={}", film.getId());
            throw new FilmNotFoundException();
        }
        films.put(film.getId(), film);
        log.info("Film updated: id={}, name={}", film.getId(), film.getName());
        return film;
    }
}
