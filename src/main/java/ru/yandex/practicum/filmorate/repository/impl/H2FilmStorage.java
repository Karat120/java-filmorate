package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.BaseRepository;
import ru.yandex.practicum.filmorate.repository.FilmStorage;
import ru.yandex.practicum.filmorate.repository.mappers.FilmMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Primary
public class H2FilmStorage extends BaseRepository<Film> implements FilmStorage {

    private static final String FIND_ALL_QUERY = """
            SELECT *
            FROM film
            """;

    private static final String FIND_BY_ID_QUERY = """
            SELECT *
            FROM film
            WHERE id = ?
            """;

    private static final String FIND_GENRES_QUERY = """
            SELECT genre_id
            FROM film_genre
            WHERE film_id = ?
            """;

    private static final String FIND_LIKES_QUERY = """
            SELECT user_id
            FROM film_like
            WHERE film_id = ?
            """;

    private static final String GET_TOP_N_FILMS_BY_LIKES_QUERY = """
            SELECT f.*, COUNT(fl.user_id) as likes_count
            FROM film f
            LEFT JOIN film_like fl ON f.id = fl.film_id
            GROUP BY f.id
            ORDER BY likes_count DESC
            LIMIT ?
            """;

    private static final String INSERT_QUERY = """
            INSERT INTO film(name, description, release_date, duration, mpa_rating)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_QUERY = """
            UPDATE film
            SET name = ?,
                description = ?,
                release_date = ?,
                duration = ?,
                mpa_rating = ?
            WHERE id = ?
            """;

    private static final String DELETE_QUERY = """
            DELETE FROM film
            WHERE id = ?
            """;


    public H2FilmStorage(JdbcTemplate jdbc, FilmMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Film add(Film film) {
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration().toSeconds(),
                film.getRating() != null ? film.getRating().getDbValue() : null
        );
        film.setId(id);
        return film;
    }

    @Override
    public Optional<Film> getById(Long id) {
        Optional<Film> film = findOne(FIND_BY_ID_QUERY, id);
        film.ifPresent(this::loadFilmData);
        return film;
    }

    @Override
    public List<Film> getAll() {
        List<Film> films = findMany(FIND_ALL_QUERY);
        films.forEach(this::loadFilmData);
        return films;
    }

    @Override
    public List<Film> getTopNFilmsByLikes(int n) {
        List<Film> films = findMany(GET_TOP_N_FILMS_BY_LIKES_QUERY, n);
        films.forEach(this::loadFilmData);
        return films;
    }

    @Override
    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration().toSeconds(),
                film.getRating() != null ? film.getRating().getDbValue() : null,
                film.getId()
        );
        return film;
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_QUERY, id);
    }

    private void loadFilmData(Film film) {
        loadGenres(film);
        loadLikes(film);
    }

    private void loadGenres(Film film) {
        Set<Long> genreIds = new HashSet<>(jdbc.queryForList(FIND_GENRES_QUERY, Long.class, film.getId()));
        FilmMapper.loadGenres(film, genreIds);
    }

    private void loadLikes(Film film) {
        Set<Long> likeIds = new HashSet<>(jdbc.queryForList(FIND_LIKES_QUERY, Long.class, film.getId()));
        FilmMapper.loadLikes(film, likeIds);
    }
}