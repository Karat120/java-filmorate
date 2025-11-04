package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmStorage filmStorage;

    @InjectMocks
    private FilmService filmService;

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

        var created = filmService.create(film);

        assertEquals(1L, film.getId());
        assertEquals(film, created);

        verify(filmStorage).add(film);
    }

    @Test
    void getById_shouldReturnFilmIfExists() {
        when(filmStorage.getById(1L)).thenReturn(Optional.of(film));

        var result = filmService.getById(1L);

        assertEquals(film, result);
        verify(filmStorage).getById(1L);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        when(filmStorage.getById(99L)).thenReturn(Optional.empty());
        assertThrows(FilmNotFoundException.class, () -> filmService.getById(99L));
    }

    @Test
    void getAll_shouldDelegateToStorage() {
        when(filmStorage.getAll()).thenReturn(List.of(film));

        var films = filmService.getAll();

        assertEquals(1, films.size());
        assertEquals(film, films.get(0));
        verify(filmStorage).getAll();
    }

    @Test
    void update_shouldDelegateToStorage() {
        when(filmStorage.update(film)).thenReturn(film);

        var updated = filmService.update(film);

        assertEquals(film, updated);
        verify(filmStorage).update(film);
    }

    @Test
    void delete_shouldDelegateToStorage() {
        film.setId(5L);

        filmService.delete(film);

        verify(filmStorage).delete(5L);
    }

    @Test
    void getTopTenFilmsByLikes_shouldRequestTop10FromStorage() {
        var topFilms = List.of(film);
        when(filmStorage.getTopNFilmsByLikes(10)).thenReturn(topFilms);

        var result = filmService.getTopNFilmsByLikes(10);

        assertEquals(topFilms, result);
        verify(filmStorage).getTopNFilmsByLikes(10);
    }
}

