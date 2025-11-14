package ru.yandex.practicum.filmorate.film.presentation.rest.mapper;

import ru.yandex.practicum.filmorate.film.domain.model.MpaRating;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.CreateMpaRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.UpdateMpaRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaRatingView;

public class MpaMapper {

    // CreateMpaRatingRequest -> MpaRating
    public static MpaRating toDomain(CreateMpaRequest dto) {
        MpaRating rating = new MpaRating();
        rating.setName(dto.name());
        return rating;
    }

    // UpdateMpaRatingRequest -> MpaRating
    public static MpaRating toDomain(UpdateMpaRequest dto) {
        MpaRating rating = new MpaRating();
        rating.setId(dto.id());
        rating.setName(dto.name());
        return rating;
    }

    // MpaRating -> MpaRatingView
    public static MpaRatingView toView(MpaRating rating) {
        return new MpaRatingView(
                rating.getId(),
                rating.getName()
        );
    }
}
