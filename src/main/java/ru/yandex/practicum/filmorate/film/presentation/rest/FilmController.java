package ru.yandex.practicum.filmorate.film.presentation.rest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.application.service.FilmUseCase;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmUseCase filmUseCase;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        filmUseCase.create(film);

        log.info("Film created: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        var films = filmUseCase.getAll();

        log.debug("Retrieving all films (total={})", films.size());
        return films;
    }

    @GetMapping("/popular")
    public List<Film> getTopNFilmsByLikes(@RequestParam int count) {
        return filmUseCase.getTopNFilmsByLikes(count);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmUseCase.update(film);

        log.info("Film updated: id={}, name={}", film.getId(), film.getName());
        return film;
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmUseCase.deleteById(id);

        log.info("Film deleted: id={}", id);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void like(@PathVariable Long filmId, @PathVariable Long userId) {
        filmUseCase.like(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void unlike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmUseCase.unlike(filmId, userId);
    }
}
