package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        log.error("Validation error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage() != null ? ex.getMessage() : "User not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FilmNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFilmNotFoundException(FilmNotFoundException ex) {
        log.warn("Film not found: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage() != null ? ex.getMessage() : "Film not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FriendshipViolationException.class)
    public ResponseEntity<Map<String, String>> handleFriendshipViolationException(FriendshipViolationException ex) {
        log.warn("Friendship violation: {}", ex.getMessage());

        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage() != null ? ex.getMessage() : "Users must be friends to perform this action");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Для любых неожиданных ошибок
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception ex) {
        log.error("Unexpected error", ex);
        Map<String, String> body = new HashMap<>();
        body.put("error", "Unexpected error: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
