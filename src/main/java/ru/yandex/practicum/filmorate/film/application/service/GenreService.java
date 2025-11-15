package ru.yandex.practicum.filmorate.film.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.repository.GenreStorage;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreView;
import ru.yandex.practicum.filmorate.film.presentation.rest.mapper.GenreMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreStorage genreStorage;

    public GenreView getById(Long id) {
        var genre = genreStorage.getById(id).orElseThrow(GenreNotFoundException::new);
        return GenreMapper.toView(genre);
    }

    public List<GenreView> getAll() {
        var genres = genreStorage.getAll();
        return GenreMapper.toViewList(genres);
    }
}
