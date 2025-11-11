package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.yandex.practicum.filmorate.film.domain.model.Film;
import ru.yandex.practicum.filmorate.film.infrastructure.repository.h2.impl.H2FilmStorage;
import ru.yandex.practicum.filmorate.film.infrastructure.repository.h2.mapper.FilmMapper;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({H2FilmStorage.class, FilmMapper.class})
@ActiveProfiles("test")
class H2FilmStorageTest {

    @Autowired
    private H2FilmStorage filmStorage;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    void clearDatabase() {
        jdbc.update("DELETE FROM film_like");
        jdbc.update("DELETE FROM film_genre");
        jdbc.update("DELETE FROM film");
        jdbc.update("DELETE FROM user_account");
    }

    @Test
    @DisplayName("Добавление фильма и получение по ID")
    void addAndGetById() {
        Film film = new Film();
        film.setName("Inception");
        film.setDescription("Dream within a dream");
        film.setReleaseDate(LocalDate.of(2010, 7, 16));
        film.setDuration(Duration.ofMinutes(148));

        Film saved = filmStorage.add(film);
        Optional<Film> found = filmStorage.getById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Inception");
    }

    @Test
    @DisplayName("Обновление фильма")
    void updateFilm() {
        Film film = new Film();
        film.setName("Old");
        film.setDescription("Initial");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(Duration.ofMinutes(100));

        Film saved = filmStorage.add(film);
        saved.setName("Updated");
        saved.setDescription("Updated description");
        saved.setDuration(Duration.ofMinutes(120));

        filmStorage.update(saved);

        Optional<Film> found = filmStorage.getById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Updated");
        assertThat(found.get().getDescription()).isEqualTo("Updated description");
    }

    @Test
    @DisplayName("Удаление фильма")
    void deleteFilm() {
        Film film = new Film();
        film.setName("To Delete");
        film.setDescription("Will be deleted");
        film.setReleaseDate(LocalDate.of(2001, 1, 1));
        film.setDuration(Duration.ofMinutes(90));

        Film saved = filmStorage.add(film);
        filmStorage.delete(saved.getId());

        Optional<Film> found = filmStorage.getById(saved.getId());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Получение всех фильмов")
    void getAllFilms() {
        Film film1 = new Film();
        film1.setName("First");
        film1.setDescription("Desc1");
        film1.setReleaseDate(LocalDate.of(2001, 1, 1));
        film1.setDuration(Duration.ofMinutes(100));
        filmStorage.add(film1);

        Film film2 = new Film();
        film2.setName("Second");
        film2.setDescription("Desc2");
        film2.setReleaseDate(LocalDate.of(2002, 2, 2));
        film2.setDuration(Duration.ofMinutes(120));
        filmStorage.add(film2);

        List<Film> all = filmStorage.getAll();
        assertThat(all)
                .hasSize(2)
                .extracting(Film::getName)
                .containsExactlyInAnyOrder("First", "Second");
    }

    @Test
    @DisplayName("Получение топ-N фильмов по лайкам")
    void getTopNFilmsByLikes() {
        Film film1 = new Film();
        film1.setName("Top1");
        film1.setDescription("Desc");
        film1.setReleaseDate(LocalDate.of(2001, 1, 1));
        film1.setDuration(Duration.ofMinutes(90));
        Film top1 = filmStorage.add(film1);

        Film film2 = new Film();
        film2.setName("Top2");
        film2.setDescription("Desc");
        film2.setReleaseDate(LocalDate.of(2002, 2, 2));
        film2.setDuration(Duration.ofMinutes(100));
        Film top2 = filmStorage.add(film2);

        // Добавляем пользователей и лайки
        jdbc.update("INSERT INTO user_account(email, login, name, birthday) VALUES('a@a.com', 'a', 'A', '2000-01-01')");
        jdbc.update("INSERT INTO user_account(email, login, name, birthday) VALUES('b@b.com', 'b', 'B', '2000-01-01')");
        jdbc.update("INSERT INTO user_account(email, login, name, birthday) VALUES('c@c.com', 'c', 'C', '2000-01-01')");

        // Получаем реальные id пользователей
        Long user1 = jdbc.queryForObject("SELECT id FROM user_account WHERE email='a@a.com'", Long.class);
        Long user2 = jdbc.queryForObject("SELECT id FROM user_account WHERE email='b@b.com'", Long.class);
        Long user3 = jdbc.queryForObject("SELECT id FROM user_account WHERE email='c@c.com'", Long.class);

        // Теперь добавляем лайки
        jdbc.update("INSERT INTO film_like(film_id, user_id) VALUES (?, ?)", top1.getId(), user1);
        jdbc.update("INSERT INTO film_like(film_id, user_id) VALUES (?, ?)", top1.getId(), user2);
        jdbc.update("INSERT INTO film_like(film_id, user_id) VALUES (?, ?)", top2.getId(), user3);

        List<Film> top = filmStorage.getTopNFilmsByLikes(2);

        assertThat(top).hasSize(2);
        assertThat(top.get(0).getId()).isEqualTo(top1.getId()); // 2 лайка
    }
}
