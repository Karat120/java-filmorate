package ru.yandex.practicum.filmorate.film.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.repository.MpaStorage;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaView;
import ru.yandex.practicum.filmorate.film.presentation.rest.mapper.MpaMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public MpaView getById(Long id) {
        var mpa = mpaStorage.getById(id).orElseThrow(MpaNotFoundException::new);
        return MpaMapper.toView(mpa);
    }

    public List<MpaView> getAll() {
        var mpas = mpaStorage.getAll();
        return mpas.stream().map(MpaMapper::toView).toList();
    }
}
