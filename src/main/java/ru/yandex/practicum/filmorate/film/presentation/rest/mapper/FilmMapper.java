package ru.yandex.practicum.filmorate.film.presentation.rest.mapper;

import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.CreateFilmRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.FilmView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreReference;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaReference;

import java.util.Set;
import java.util.stream.Collectors;

public class FilmMapper {
    // CreateFilmRequest -> Film
    public static Film toDomain(CreateFilmRequest dto) {
        Film film = new Film();
        film.setName(dto.name());
        film.setDescription(dto.description());
        film.setReleaseDate(dto.releaseDate());
        film.setDuration(dto.duration());
        film.setMpa(dto.mpa().id());

        if (dto.genres() != null) {
            film.setGenres(dto.genres().stream().map(GenreReference::id).collect(Collectors.toSet()));
        }

        return film;
    }

    // UpdateFilmRequest -> Film
    public static Film toDomain(UpdateFilmRequest dto) {
        Film film = new Film();
        film.setId(dto.id());
        film.setName(dto.name());
        film.setDescription(dto.description());
        film.setReleaseDate(dto.releaseDate());
        film.setDuration(dto.duration());
        film.setMpa(dto.mpa().id());

        if (dto.userLikes() != null) {
            film.setUserLikes(dto.userLikes());
        }

        if (dto.genres() != null) {
            film.setGenres(dto.genres().stream().map(GenreReference::id).collect(Collectors.toSet()));
        }

        return film;
    }

    // Film -> FilmView
    public static FilmView toView(Film film, MpaReference mpaReference, Set<GenreReference> genreReferences) {
        return new FilmView(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                mpaReference,
                (long) film.countLikes(),
                genreReferences
        );
    }
}