package ru.yandex.practicum.filmorate.shared.domain.service;

@FunctionalInterface
public interface IdGenerator<T> {
    T generate();
}
