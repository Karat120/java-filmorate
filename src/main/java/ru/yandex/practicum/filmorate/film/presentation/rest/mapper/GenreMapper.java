package ru.yandex.practicum.filmorate.film.presentation.rest.mapper;

import ru.yandex.practicum.filmorate.film.domain.model.Genre;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.CreateGenreRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.UpdateGenreRequest;

import java.util.Collection;
import java.util.List;

public class GenreMapper {

    // CreateGenreRequest -> Genre
    public static Genre toDomain(CreateGenreRequest dto) {
        Genre genre = new Genre();
        genre.setName(dto.name());
        return genre;
    }

    // UpdateGenreRequest -> Genre
    public static Genre toDomain(UpdateGenreRequest dto) {
        Genre genre = new Genre();
        genre.setId(dto.id());
        genre.setName(dto.name());
        return genre;
    }

    // Genre -> GenreView
    public static GenreView toView(Genre genre) {
        if (genre == null) {
            return null;
        }
        return new GenreView(
                genre.getId(),
                genre.getName()
        );
    }

    // Collection<Genre> -> Set<GenreView>
    public static List<GenreView> toViewList(Collection<Genre> genres) {
        if (genres == null) {
            return List.of();
        }
        return genres.stream()
                .map(GenreMapper::toView)
                .toList();
    }

    // Set<Long> genreIds -> Set<Genre> (для создания объектов Genre только с ID)
    public static List<Genre> toDomainList(List<Long> genreIds) {
        if (genreIds == null) {
            return List.of();
        }
        return genreIds.stream()
                .map(genreId -> {
                    Genre genre = new Genre();
                    genre.setId(genreId);
                    return genre;
                })
                .toList();
    }

    // Set<Genre> -> Set<Long> (извлечение ID из жанров)
    public static List<Long> toIdList(List<Genre> genres) {
        if (genres == null) {
            return List.of();
        }
        return genres.stream()
                .map(Genre::getId)
                .toList();
    }

    // Создание Genre только с ID (для связей)
    public static Genre createWithId(Long id) {
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}