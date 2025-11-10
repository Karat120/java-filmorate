package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.repository.BaseRepository;
import ru.yandex.practicum.filmorate.repository.MpaStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class H2MpaRatingStorage extends BaseRepository<MpaRating> implements MpaStorage {
    public H2MpaRatingStorage(JdbcTemplate jdbc, RowMapper<MpaRating> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public MpaRating add(MpaRating mpaRating) {
        return null;
    }

    @Override
    public Optional<MpaRating> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<MpaRating> getAll() {
        return null;
    }

    @Override
    public MpaRating update(MpaRating mpa) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
