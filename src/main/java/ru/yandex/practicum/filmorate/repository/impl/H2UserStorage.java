package ru.yandex.practicum.filmorate.repository.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.BaseRepository;
import ru.yandex.practicum.filmorate.repository.UserStorage;
import ru.yandex.practicum.filmorate.repository.mappers.UserMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Primary
public class H2UserStorage extends BaseRepository<User> implements UserStorage {
    private static final String FIND_ALL_QUERY = """
            SELECT *
            FROM user_account
            """;

    private static final String FIND_BY_ID_QUERY = """
            SELECT *
            FROM user_account
            WHERE id = ?
            """;

    private static final String FIND_FRIENDS_QUERY = """
            SELECT friend_id
            FROM friendship
            WHERE user_id = ?
            """;

    private static final String EXISTS_BY_ID_QUERY = """
            SELECT COUNT(*)
            FROM user_account
            WHERE id = ?
            """;

    private static final String FIND_BY_IDS_QUERY = """
            SELECT *
            FROM user_account
            WHERE id IN (%s)
            """;

    private static final String INSERT_QUERY = """
            INSERT INTO user_account(email, login, name, birthday)
            VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE_QUERY = """
            UPDATE user_account
            SET email = ?,
                login = ?,
                name = ?,
                birthday = ?
            WHERE id = ?
            """;

    private static final String DELETE_QUERY = """
            DELETE FROM user_account
            WHERE id = ?
            """;

    public H2UserStorage(JdbcTemplate jdbc, UserMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public User add(User user) {
        long id = insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        Optional<User> user = findOne(FIND_BY_ID_QUERY, id);
        user.ifPresent(this::loadFriends);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = findMany(FIND_ALL_QUERY);
        users.forEach(this::loadFriends);
        return users;
    }

    @Override
    public List<User> getAllByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        String inClause = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String query = String.format(FIND_BY_IDS_QUERY, inClause);

        List<User> users = findMany(query);
        users.forEach(this::loadFriends);
        return users;
    }

    @Override
    public User update(User user) {
        update(
                UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    @Override
    public void delete(Long id) {
        delete(DELETE_QUERY, id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbc.queryForObject(EXISTS_BY_ID_QUERY, Integer.class, id);
        return count != null && count > 0;
    }

    private void loadFriends(User user) {
        Set<Long> friendIds = new HashSet<>(jdbc.queryForList(FIND_FRIENDS_QUERY, Long.class, user.getId()));
        UserMapper.loadFriends(user, friendIds);
    }
}