package ru.yandex.practicum.filmorate.repository.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserStorage;
import ru.yandex.practicum.filmorate.util.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private final IdGenerator<Long> idGenerator;

    @Override
    public User add(User user) {
        user.setId(idGenerator.generate());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> getAllByIds(List<Long> ids) {
        var idsSet = new HashSet<>(ids);
        return users.values().stream()
                .filter(user -> idsSet.contains(user.getId()))
                .toList();
    }

    @Override
    public User update(User user) {
        if (user.getId() == null || !users.containsKey(user.getId())) {
            throw new UserNotFoundException();
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(Long id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException();
        }
        users.remove(id);
    }
}
