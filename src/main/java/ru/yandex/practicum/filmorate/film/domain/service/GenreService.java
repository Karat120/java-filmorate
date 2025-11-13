package ru.yandex.practicum.filmorate.film.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.Genre;
import ru.yandex.practicum.filmorate.film.domain.repository.GenreStorage;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public void update(Genre genre) {
        if (genreStorage.getById(genre.getId()).isEmpty()) {
            throw new GenreNotFoundException();
        }
        genreStorage.update(genre);
    }

    public void delete(Genre genre) {
        if (genreStorage.getById(genre.getId()).isEmpty()) {
            throw new GenreNotFoundException();
        }
        genreStorage.delete(genre.getId());
    }
}
