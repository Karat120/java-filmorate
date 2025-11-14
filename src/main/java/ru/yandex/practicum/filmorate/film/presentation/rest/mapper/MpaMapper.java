package ru.yandex.practicum.filmorate.film.presentation.rest.mapper;

import ru.yandex.practicum.filmorate.film.domain.model.Mpa;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.CreateMpaRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.UpdateMpaRequest;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaView;

public class MpaMapper {

    // CreateMpaRatingRequest -> MpaRating
    public static Mpa toDomain(CreateMpaRequest dto) {
        Mpa rating = new Mpa();
        rating.setName(dto.name());
        return rating;
    }

    // UpdateMpaRatingRequest -> MpaRating
    public static Mpa toDomain(UpdateMpaRequest dto) {
        Mpa rating = new Mpa();
        rating.setId(dto.id());
        rating.setName(dto.name());
        return rating;
    }

    // MpaRating -> MpaRatingView
    public static MpaView toView(Mpa rating) {
        return new MpaView(
                rating.getId(),
                rating.getName()
        );
    }
}
