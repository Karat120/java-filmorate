package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.BaseRepository;
import ru.yandex.practicum.filmorate.repository.GenreStorage;

import java.util.List;
import java.util.Optional;


@Repository
@Primary
public class H2GenreStorage extends BaseRepository<Genre> implements GenreStorage {
    private static final String INSERT_SQL = "INSERT INTO genre (name) VALUES (?)";
    private static final String SELECT_BY_ID_SQL = "SELECT id, name FROM genre WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT id, name FROM genre ORDER BY id";
    private static final String UPDATE_SQL = "UPDATE genre SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM genre WHERE id = ?";

    public H2GenreStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Genre add(Genre genre) {
        Long id = insert(INSERT_SQL, genre.getName());
        genre.setId(id);
        return genre;
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return findOne(SELECT_BY_ID_SQL, id);
    }

    @Override
    public List<Genre> getAll() {
        return findMany(SELECT_ALL_SQL);
    }

    @Override
    public Genre update(Genre genre) {
        update(UPDATE_SQL, genre.getName(), genre.getId());
        return genre;
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_SQL, id);
    }
}
