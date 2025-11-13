package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.film.domain.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.film.application.service.FilmUseCase;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.domain.repository.FilmStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmUseCaseTest {

    @Mock
    private FilmStorage filmStorage;

    @InjectMocks
    private FilmUseCase filmUseCase;

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setName("Matrix");
        film.setId(1L);
    }

    @Test
    void create_shouldDelegateToStorage() {
        when(filmStorage.add(film)).thenReturn(film);

        var created = filmUseCase.create(film);

        assertEquals(1L, film.getId());
        assertEquals(film, created);

        verify(filmStorage).add(film);
    }

    @Test
    void getById_shouldReturnFilmIfExists() {
        when(filmStorage.getById(1L)).thenReturn(Optional.of(film));

        var result = filmUseCase.getById(1L);

        assertEquals(film, result);
        verify(filmStorage).getById(1L);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(filmStorage.getById(99L)).thenReturn(Optional.empty());
        assertThrows(FilmNotFoundException.class, () -> filmUseCase.getById(99L));
    }

    @Test
    void getAll_shouldDelegateToStorage() {
        when(filmStorage.getAll()).thenReturn(List.of(film));

        var films = filmUseCase.getAll();

        assertEquals(1, films.size());
        assertEquals(film, films.get(0));
        verify(filmStorage).getAll();
    }

    @Test
    void update_shouldDelegateToStorage() {
        when(filmStorage.update(film)).thenReturn(film);

        var updated = filmUseCase.update(film);

        assertEquals(film, updated);
        verify(filmStorage).update(film);
    }

    @Test
    void delete_shouldDelegateToStorage() {
        film.setId(5L);

        filmUseCase.delete(film);

        verify(filmStorage).delete(5L);
    }

    @Test
    void getTopTenFilmsByLikes_shouldRequestTop10FromStorage() {
        var topFilms = List.of(film);
        when(filmStorage.getTopNFilmsByLikes(10)).thenReturn(topFilms);

        var result = filmUseCase.getTopNFilmsByLikes(10);

        assertEquals(topFilms, result);
        verify(filmStorage).getTopNFilmsByLikes(10);
    }
}

