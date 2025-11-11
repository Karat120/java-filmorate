package ru.yandex.practicum.filmorate.shared.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {
    private int status;
    private LocalDateTime timestamp;
    private String path;
    private String message;
}
