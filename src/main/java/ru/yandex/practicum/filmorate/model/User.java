package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Long id;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must contain '@' and be valid")
    private String email;

    @NotBlank(message = "Login cannot be empty")
    @Pattern(regexp = "\\S+", message = "Login must not contain spaces")
    private String login;

    private String name;

    @PastOrPresent(message = "Birthday cannot be in the future")
    private LocalDate birthday;

    public void setLogin(String login) {
        this.login = login;
        if (this.name == null || this.name.isBlank()) {
            this.name = login;
        }
    }
}