package ru.yandex.practicum.filmorate.film.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.domain.model.Genre;
import ru.yandex.practicum.filmorate.film.domain.repository.FilmStorage;
import ru.yandex.practicum.filmorate.film.domain.repository.GenreStorage;
import ru.yandex.practicum.filmorate.film.domain.repository.MpaStorage;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.CreateFilmRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.FilmView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreReference;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaReference;
import ru.yandex.practicum.filmorate.film.presentation.rest.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.user.domain.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.user.domain.repository.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilmUseCase {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    public FilmView create(CreateFilmRequest film) {
        var newFilm = FilmMapper.toDomain(film);

        mpaStorage.getById(newFilm.getMpa()).orElseThrow(MpaNotFoundException::new);
        var genres = genreStorage.getAll().stream().map(Genre::getId).collect(Collectors.toSet());
        if (newFilm.getGenres().stream().anyMatch(genre -> !genres.contains(genre))) {
            throw new GenreNotFoundException();
        }

        filmStorage.add(newFilm);

        return toView(newFilm);
    }

    public FilmView getById(Long id) {
        var film = filmStorage.getById(id).orElseThrow(FilmNotFoundException::new);
        return toView(film);
    }

    public List<FilmView> getAll() {
        var films = filmStorage.getAll();
        return films.stream().map(this::toView).toList();
    }

    public FilmView update(UpdateFilmRequest film) {
        var filmToUpdate = FilmMapper.toDomain(film);
        filmStorage.update(filmToUpdate);
        return toView(filmToUpdate);
    }

    public void delete(Long id) {
        filmStorage.delete(id);
    }

    public List<FilmView> getTopNFilmsByLikes(int n) {
        var films = filmStorage.getTopNFilmsByLikes(n);
        return films.stream().map(this::toView).toList();
    }

    public void like(Long filmId, Long likedById) {
        var film = filmStorage.getById(filmId).orElseThrow(FilmNotFoundException::new);
        if (!userStorage.existsById(likedById)) {
            throw new UserNotFoundException();
        }

        film.likeBy(likedById);

        filmStorage.update(film);
    }

    public void unlike(Long filmId, Long userId) {
        var film = filmStorage.getById(filmId).orElseThrow(FilmNotFoundException::new);
        if (!userStorage.existsById(userId)) {
            throw new UserNotFoundException();
        }

        film.unlikeBy(userId);

        filmStorage.update(film);
    }

    private FilmView toView(Film film) {
        return FilmMapper.toView(film,
                new MpaReference(film.getMpa()),
                film.getGenres().stream().map(GenreReference::new).collect(Collectors.toSet()));
    }
}
