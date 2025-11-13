package ru.yandex.practicum.filmorate.user.domain.repository;

import ru.yandex.practicum.filmorate.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    void add(User user);

    Optional<User> getById(Long id);

    List<User> getAll();

    List<User> getAllByIds(List<Long> ids);

    void update(User user);

    void delete(Long id);

    boolean existsById(Long id);
}
