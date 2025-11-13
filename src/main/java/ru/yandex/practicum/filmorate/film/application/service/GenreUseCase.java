package ru.yandex.practicum.filmorate.film.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.Genre;
import ru.yandex.practicum.filmorate.film.domain.repository.GenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreUseCase {
    private final GenreStorage genreStorage;

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
        genreStorage.update(genre);
        return genre;
    }

    public void delete(Long id) {
        genreStorage.getById(id).orElseThrow(GenreNotFoundException::new);
        genreStorage.delete(id);
    }
}
