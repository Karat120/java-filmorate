package ru.yandex.practicum.filmorate.film.presentation.rest.mapper;

import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.CreateFilmRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.FilmView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaRatingView;

import java.util.Set;

public class FilmMapper {
    // CreateFilmRequest -> Film
    public static Film toDomain(CreateFilmRequest dto) {
        Film film = new Film();
        film.setName(dto.name());
        film.setDescription(dto.description());
        film.setReleaseDate(dto.releaseDate());
        film.setDuration(dto.durationMinutes());
        film.setMpaRating(dto.mpaRating());

        if (dto.genres() != null) {
            film.setGenres(dto.genres());
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
        film.setDuration(dto.durationMinutes());
        film.setMpaRating(dto.mpaRating());

        if (dto.userLikes() != null) {
            film.setUserLikes(dto.userLikes());
        }

        if (dto.genres() != null) {
            film.setGenres(dto.genres());
        }

        return film;
    }

    // Film -> FilmView
    public static FilmView toView(Film film, MpaRatingView mpaRatingView, Set<GenreView> genreViews) {
        return new FilmView(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                mpaRatingView,
                (long) film.countLikes(),
                genreViews
        );
    }
}