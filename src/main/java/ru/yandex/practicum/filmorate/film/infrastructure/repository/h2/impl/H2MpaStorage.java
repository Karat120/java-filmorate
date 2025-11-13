package ru.yandex.practicum.filmorate.film.infrastructure.repository.h2.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.shared.infrastructure.repository.BaseRepository;
import ru.yandex.practicum.filmorate.film.domain.model.MpaRating;
import ru.yandex.practicum.filmorate.film.domain.repository.MpaStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class H2MpaStorage extends BaseRepository<MpaRating> implements MpaStorage {

    private static final String INSERT_SQL = "INSERT INTO mpa (name) VALUES (?)";
    private static final String SELECT_BY_ID_SQL = "SELECT id, name FROM mpa WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT id, name FROM mpa ORDER BY id";
    private static final String UPDATE_SQL = "UPDATE mpa SET name = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM mpa WHERE id = ?";

    public H2MpaStorage(JdbcTemplate jdbc, RowMapper<MpaRating> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void add(MpaRating mpaRating) {
        Long id = insert(INSERT_SQL, mpaRating.getName());
        mpaRating.setId(id);
    }

    @Override
    public Optional<MpaRating> getById(Long id) {
        return findOne(SELECT_BY_ID_SQL, id);
    }

    @Override
    public List<MpaRating> getAll() {
        return findMany(SELECT_ALL_SQL);
    }

    @Override
    public void update(MpaRating mpaRating) {
        update(UPDATE_SQL, mpaRating.getName(), mpaRating.getId());
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_SQL, id);
    }
}
