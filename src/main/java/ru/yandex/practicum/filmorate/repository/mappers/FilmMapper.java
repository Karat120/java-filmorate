package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

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

        String mpaRating = rs.getString("mpa_rating");
        if (mpaRating != null) {
            film.setRating(MpaRating.valueOf(mpaRating));
        }

        film.setGenres(new HashSet<>());
        film.setUserLikes(new HashSet<>());
        return film;
    }

    public static void loadGenres(Film film, Set<Long> genreIds) {
        film.setGenres(genreIds);
    }

    public static void loadLikes(Film film, Set<Long> likeIds) {
        film.setUserLikes(likeIds);
    }
}