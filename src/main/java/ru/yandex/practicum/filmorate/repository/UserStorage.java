package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User add(User user);

    Optional<User> getById(Long id);

    List<User> getAll();

    List<User> getAllByIds(List<Long> ids);

    User update(User user);

    void delete(Long id);
}
