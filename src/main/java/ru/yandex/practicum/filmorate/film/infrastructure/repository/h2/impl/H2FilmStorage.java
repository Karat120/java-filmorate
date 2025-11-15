package ru.yandex.practicum.filmorate.film.infrastructure.repository.h2.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.domain.repository.FilmStorage;
import ru.yandex.practicum.filmorate.film.infrastructure.repository.h2.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.shared.infrastructure.repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static final String FIND_MPA_QUERY = """
            SELECT mpa_id
            FROM film
            WHERE id = ?
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
            INSERT INTO film(name, description, release_date, duration, mpa_id)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_QUERY = """
            UPDATE film
            SET name = ?,
                description = ?,
                release_date = ?,
                duration = ?,
                mpa_id= ?
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
    public void add(Film film) {
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration().toMinutes(),
                film.getMpa()
        );
        film.setId(id);

        updateFilmLikes(film);

        updateFilmGenres(film);
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
    public void update(Film film) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration().toMinutes(),
                film.getMpa(),
                film.getId()
        );

        updateFilmLikes(film);

        updateFilmGenres(film);
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_QUERY, id);
    }

    private void updateFilmLikes(Film film) {
        // Удаляем все текущие лайки фильма
        delete("DELETE FROM film_like WHERE film_id = ?", film.getId());

        // Добавляем новые лайки
        if (film.getUserLikes() != null) {
            List<Object[]> args = film.getUserLikes().stream()
                    .map(userId -> new Object[]{film.getId(), userId})
                    .collect(Collectors.toList());

            jdbc.batchUpdate(
                    "INSERT INTO film_like (film_id, user_id) VALUES (?, ?)",
                    args
            );
        }
    }

    private void updateFilmGenres(Film film) {
        delete("DELETE FROM film_genre WHERE film_id = ?", film.getId());

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            List<Object[]> args = film.getGenres().stream()
                    .map(genreId -> new Object[]{film.getId(), genreId})
                    .collect(Collectors.toList());

            jdbc.batchUpdate(
                    "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)",
                    args
            );
        }
    }

    private void loadFilmData(Film film) {
        loadGenres(film);
        loadLikes(film);
        loadMpaRating(film);
    }

    private void loadGenres(Film film) {
        var genreIds = jdbc.queryForList(FIND_GENRES_QUERY, Long.class, film.getId());
        FilmMapper.loadGenres(film, genreIds);
    }

    private void loadLikes(Film film) {
        var likeIds = jdbc.queryForList(FIND_LIKES_QUERY, Long.class, film.getId());
        FilmMapper.loadLikes(film, likeIds);
    }

    private void loadMpaRating(Film film) {
        Long mpaId = jdbc.queryForObject(FIND_MPA_QUERY, Long.class, film.getId());
        FilmMapper.loadMpa(film, mpaId);
    }
}