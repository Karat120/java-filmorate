package ru.yandex.practicum.filmorate.film.infrastructure.repository.h2.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.film.domain.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));

        java.sql.Date releaseDate = rs.getDate("release_date");
        if (releaseDate != null) {
            film.setReleaseDate(releaseDate.toLocalDate());
        }

        var duration = Duration.ofSeconds(rs.getLong("duration"));
        film.setDuration(duration);

        film.setGenres(new ArrayList<>());
        film.setUserLikes(new ArrayList<>());
        return film;
    }

    public static void loadGenres(Film film, List<Long> genreIds) {
        film.setGenres(genreIds);
    }

    public static void loadMpa(Film film, Long mpaId) {film.setMpa(mpaId);}

    public static void loadLikes(Film film, List<Long> likeIds) {
        film.setUserLikes(likeIds);
    }
}