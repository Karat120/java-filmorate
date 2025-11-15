package ru.yandex.practicum.filmorate.film.presentation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.film.presentation.annotation.NotBeforeCinemaBirthday;

import java.time.LocalDate;

public class NotBeforeCinemaBirthdayValidator implements ConstraintValidator<NotBeforeCinemaBirthday, LocalDate> {

    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value != null && !value.isBefore(CINEMA_BIRTHDAY);
    }
}
