package ru.yandex.practicum.filmorate.film.presentation.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.film.application.service.GenreUseCase;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreView;

import java.util.List;

@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {
    private final GenreUseCase genreUseCase;

    @GetMapping
    public List<GenreView> getAll() {
        return genreUseCase.getAll();
    }

    @GetMapping("/{id}")
    public GenreView getById(@PathVariable Long id) {
        return genreUseCase.getById(id);
    }
}
