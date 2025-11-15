package ru.yandex.practicum.filmorate.shared.presentation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.film.domain.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.shared.presentation.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import ru.yandex.practicum.filmorate.user.domain.exception.FriendshipViolationException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserAlreadyLikedException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserHasNotLikedException;
import ru.yandex.practicum.filmorate.user.domain.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.exception.FilmNotFoundException;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponseDto buildErrorResponse(Exception ex, HttpStatus status, HttpServletRequest request) {
        return ErrorResponseDto.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .message(ex.getMessage() != null ? ex.getMessage() : status.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {
        log.error("Validation error: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex,
                                                                        HttpServletRequest request) {
        log.warn("User not found: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyLikedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyLikedException(UserAlreadyLikedException ex,
                                                                            HttpServletRequest request) {
        log.warn("User already liked the film: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserHasNotLikedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserHasNotLikedException(UserHasNotLikedException ex,
                                                                           HttpServletRequest request) {
        log.warn("User has not liked the film: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FilmNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleFilmNotFoundException(FilmNotFoundException ex,
                                                                        HttpServletRequest request) {
        log.warn("Film not found: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MpaNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMpaNotFoundException(MpaNotFoundException ex,
                                                                        HttpServletRequest request) {
        log.warn("Mpa not found: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGenreFoundException(GenreNotFoundException ex,
                                                                        HttpServletRequest request) {
        log.warn("Genre not found: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FriendshipViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleFriendshipViolationException(FriendshipViolationException ex,
                                                                               HttpServletRequest request) {
        log.warn("Friendship violation: {}", ex.getMessage());
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.CONFLICT, request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleOtherExceptions(Exception ex,
                                                                  HttpServletRequest request) {
        log.error("Unexpected error", ex);
        ErrorResponseDto body = buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
