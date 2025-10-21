package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void createFilm_shouldAssignIdAndStoreFilm() {
        Film film = new Film();
        film.setName("Inception");

        Film created = filmController.createFilm(film);

        assertThat(created.getId()).isZero(); // первый id = 0
        assertThat(created.getName()).isEqualTo("Inception");
        assertThat(filmController.getAllFilms()).containsExactly(created);
    }

    @Test
    void getAllFilms_shouldReturnAllCreatedFilms() {
        Film f1 = new Film();
        f1.setName("Matrix");
        Film f2 = new Film();
        f2.setName("Interstellar");

        filmController.createFilm(f1);
        filmController.createFilm(f2);

        List<Film> films = filmController.getAllFilms();

        assertThat(films).hasSize(2);
        assertThat(films).extracting(Film::getName)
                .containsExactlyInAnyOrder("Matrix", "Interstellar");
    }

    @Test
    void updateFilm_shouldReplaceExistingFilm() {
        Film film = new Film();
        film.setName("Old title");
        Film created = filmController.createFilm(film);

        Film updated = new Film();
        updated.setId(created.getId());
        updated.setName("New title");

        Film result = filmController.updateFilm(updated);

        assertThat(result.getName()).isEqualTo("New title");
        assertThat(filmController.getAllFilms()).containsExactly(result);
    }

    @Test
    void updateFilm_shouldThrowIfFilmNotExists() {
        Film nonExistent = new Film();
        nonExistent.setId(999L);
        nonExistent.setName("Ghost film");

        assertThatThrownBy(() -> filmController.updateFilm(nonExistent))
                .isInstanceOf(FilmNotFoundException.class);
    }
}
