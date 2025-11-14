package ru.yandex.practicum.filmorate.film.presentation.rest.controller;

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
import ru.yandex.practicum.filmorate.film.application.service.FilmUseCase;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.CreateFilmRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.FilmView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.UpdateFilmRequest;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmUseCase filmUseCase;

    @PostMapping
    public FilmView createFilm(@Valid @RequestBody CreateFilmRequest request) {
        var film = filmUseCase.create(request);

        log.info("Film created: name={}", film.id(), film.name());
        return film;
    }

    @GetMapping
    public List<FilmView> getAllFilms() {
        var films = filmUseCase.getAll();

        log.debug("Retrieving all films (total={})", films.size());
        return films;
    }

    @GetMapping("/{id}")
    public FilmView getFilmById(@PathVariable Long id) {
        var film = filmUseCase.getById(id);

        log.debug("Retrieving film by id: id={}", id);
        return film;
    }

    @GetMapping("/popular")
    public List<FilmView> getTopNFilmsByLikes(@RequestParam int count) {
        return filmUseCase.getTopNFilmsByLikes(count);
    }

    @PutMapping
    public FilmView updateFilm(@Valid @RequestBody UpdateFilmRequest request) {
        var film = filmUseCase.update(request);

        log.info("Film updated: id={}, name={}", film.id(), film.name());
        return film;
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable Long id) {
        filmUseCase.delete(id);

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
