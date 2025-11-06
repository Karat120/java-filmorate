package ru.yandex.practicum.filmorate.util;

@FunctionalInterface
public interface IdGenerator<T> {
    T generate();
}
