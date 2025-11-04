package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        filmService.create(film);

        log.info("Film created: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        var films = filmService.getAll();

        log.debug("Retrieving all films (total={})", films.size());
        return films;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmService.update(film);

        log.info("Film updated: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmService.deleteById(id);

        log.info("Film deleted: id={}", id);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void like(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.like(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void unlike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.unlike(filmId, userId);
    }
}
