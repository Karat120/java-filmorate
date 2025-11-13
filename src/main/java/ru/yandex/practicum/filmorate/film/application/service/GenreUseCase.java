package ru.yandex.practicum.filmorate.film.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.Genre;
import ru.yandex.practicum.filmorate.film.domain.repository.GenreStorage;
import ru.yandex.practicum.filmorate.film.domain.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreUseCase {
    private final GenreStorage genreStorage;
    private final GenreService genreService;

    public Genre add(Genre genre) {
        genreStorage.add(genre);
        return genre;
    }

    public Genre getById(Long id) {
        return genreStorage.getById(id).orElseThrow(GenreNotFoundException::new);
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    public boolean existsById(Long id) {
        return genreStorage.getById(id).isPresent();
    }

    public Genre update(Genre genre) {
        genreStorage.getById(genre.getId()).orElseThrow(GenreNotFoundException::new);
        genreService.update(genre);
        return genre;
    }

    public void delete(Long id) {
        Genre genre = genreStorage.getById(id).orElseThrow(GenreNotFoundException::new);
        genreService.delete(genre);
    }
}
