package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.user.domain.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        factory.close();
    }

    @Test
    void validUser_shouldHaveNoValidationErrors() {
        User user = new User();
        user.setLogin("alice123");
        user.setEmail("alice@example.com");
        user.setName("Alice");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    void emptyEmail_shouldFailValidation() {
        User user = new User();
        user.setLogin("alice");
        user.setEmail("");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).extracting("propertyPath")
                .extracting(Object::toString)
                .contains("email");
    }

    @Test
    void loginWithSpaces_shouldFailValidation() {
        User user = new User();
        user.setLogin("alice 123");
        user.setEmail("alice@example.com");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).extracting("propertyPath")
                .extracting(Object::toString)
                .contains("login");
    }

    @Test
    void birthdayInFuture_shouldFailValidation() {
        User user = new User();
        user.setLogin("bob");
        user.setEmail("bob@example.com");
        user.setBirthday(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).extracting("propertyPath")
                .extracting(Object::toString)
                .contains("birthday");
    }

    @Test
    void emptyName_shouldUseLogin() {
        User user = new User();
        user.setLogin("charlie");
        user.setEmail("charlie@example.com");

        assertThat(user.getName()).isEqualTo(user.getLogin());
    }
}
