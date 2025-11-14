package ru.yandex.practicum.filmorate.film.infrastructure.repository.h2.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.film.domain.model.Genre;
import ru.yandex.practicum.filmorate.film.domain.repository.GenreStorage;
import ru.yandex.practicum.filmorate.shared.infrastructure.repository.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@Primary
public class H2GenreStorage extends BaseRepository<Genre> implements GenreStorage {
    private static final String INSERT_SQL = "INSERT INTO genre (name) VALUES (?)";
    private static final String SELECT_BY_ID_SQL = "SELECT id, name FROM genre WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT id, name FROM genre ORDER BY id";
    private static final String SELECT_ALL_BY_IDS_SQL = "SELECT id, name FROM genre WHERE id IN (%s)";
    private static final String UPDATE_SQL = "UPDATE genre SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM genre WHERE id = ?";

    public H2GenreStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void add(Genre genre) {
        Long id = insert(INSERT_SQL, genre.getName());
        genre.setId(id);
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
    public List<Genre> getAllByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        String inClause = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String query = String.format(SELECT_ALL_BY_IDS_SQL, inClause);

        return findMany(query);
    }

    @Override
    public void update(Genre genre) {
        update(UPDATE_SQL, genre.getName(), genre.getId());
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_SQL, id);
    }
}
