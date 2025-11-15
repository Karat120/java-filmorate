package ru.yandex.practicum.filmorate.film.presentation.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.film.application.service.MpaService;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaView;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<MpaView> getAll() {
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public MpaView getById(@PathVariable Long id) {
        return mpaService.getById(id);
    }
}
