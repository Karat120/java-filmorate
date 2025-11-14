package ru.yandex.practicum.filmorate.film.presentation.rest.mapper;

import ru.yandex.practicum.filmorate.film.domain.model.Genre;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.CreateGenreRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.GenreView;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.genre.UpdateGenreRequest;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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
    public static Set<GenreView> toViewSet(Collection<Genre> genres) {
        if (genres == null) {
            return Set.of();
        }
        return genres.stream()
                .map(GenreMapper::toView)
                .collect(Collectors.toSet());
    }

    // Set<Long> genreIds -> Set<Genre> (для создания объектов Genre только с ID)
    public static Set<Genre> toDomainSet(Set<Long> genreIds) {
        if (genreIds == null) {
            return Set.of();
        }
        return genreIds.stream()
                .map(genreId -> {
                    Genre genre = new Genre();
                    genre.setId(genreId);
                    return genre;
                })
                .collect(Collectors.toSet());
    }

    // Set<Genre> -> Set<Long> (извлечение ID из жанров)
    public static Set<Long> toIdSet(Set<Genre> genres) {
        if (genres == null) {
            return Set.of();
        }
        return genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
    }

    // Создание Genre только с ID (для связей)
    public static Genre createWithId(Long id) {
        Genre genre = new Genre();
        genre.setId(id);
        return genre;
    }
}