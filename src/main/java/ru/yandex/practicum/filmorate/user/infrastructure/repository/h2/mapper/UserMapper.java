package ru.yandex.practicum.filmorate.user.infrastructure.repository.h2.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.user.domain.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Component
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));

        java.sql.Date birthday = rs.getDate("birthday");
        if (birthday != null) {
            user.setBirthday(birthday.toLocalDate());
        }

        return user;
    }

    public static void loadFriends(User user, Set<Long> friendIds) {
        user.setFriends(friendIds);
    }
}
