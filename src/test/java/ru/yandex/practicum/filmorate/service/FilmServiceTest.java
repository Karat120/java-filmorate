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
    void createFilm_shouldDelegateToStorage() {
        when(filmStorage.addFilm(film)).thenReturn(film);

        var created = filmService.createFilm(film);

        assertEquals(1L, film.getId());
        assertEquals(film, created);

        verify(filmStorage).addFilm(film);
    }

    @Test
    void getFilmById_shouldReturnFilmIfExists() {
        when(filmStorage.getFilmById(1L)).thenReturn(Optional.of(film));

        var result = filmService.getFilmById(1L);

        assertEquals(film, result);
        verify(filmStorage).getFilmById(1L);
    }

    @Test
    void getFIlmById_shouldThrowIfNotFound() {
        when(filmStorage.getFilmById(99L)).thenReturn(Optional.empty());
        assertThrows(FilmNotFoundException.class, () -> filmService.getFilmById(99L));
    }

    @Test
    void getAllFilms_shouldDelegateToStorage() {
        when(filmStorage.getAllFilms()).thenReturn(List.of(film));

        var films = filmService.getAllFilms();

        assertEquals(1, films.size());
        assertEquals(film, films.get(0));
        verify(filmStorage).getAllFilms();
    }

    @Test
    void updateFilm_shouldDelegateToStorage() {
        when(filmStorage.updateFilm(film)).thenReturn(film);

        var updated = filmService.updateFilm(film);

        assertEquals(film, updated);
        verify(filmStorage).updateFilm(film);
    }

    @Test
    void deleteFilm_shouldDelegateToStorage() {
        film.setId(5L);

        filmService.deleteFilm(film);

        verify(filmStorage).deleteFilm(5L);
    }

    @Test
    void getTopTenFilmsByLikes_shouldRequestTop10FromStorage() {
        var topFilms = List.of(film);
        when(filmStorage.getTopNFilmsByLikes(10)).thenReturn(topFilms);

        var result = filmService.getTopTenFilmsByLikes();

        assertEquals(topFilms, result);
        verify(filmStorage).getTopNFilmsByLikes(10);
    }
}

