package ru.yandex.practicum.filmorate.film.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.film.domain.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.film.domain.model.Mpa;
import ru.yandex.practicum.filmorate.film.domain.repository.MpaStorage;
import ru.yandex.practicum.filmorate.film.presentation.rest.dto.mpa.MpaView;
import ru.yandex.practicum.filmorate.film.presentation.rest.mapper.MpaMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class MpaService {
    private final MpaStorage mpaStorage;

    public Mpa add(Mpa mpa) {
        mpaStorage.add(mpa);
        return mpa;
    }

    public MpaView getById(Long id) {
        var mpa = mpaStorage.getById(id).orElseThrow(MpaNotFoundException::new);
        return MpaMapper.toView(mpa);
    }

    public List<MpaView> getAll() {
        var mpas = mpaStorage.getAll();
        return mpas.stream().map(MpaMapper::toView).toList();
    }

    public boolean existsById(Long id) {
        return mpaStorage.getById(id).isPresent();
    }

    public MpaView update(Mpa mpa) {
        mpaStorage.getById(mpa.getId()).orElseThrow(MpaNotFoundException::new);
        mpaStorage.update(mpa);
        return MpaMapper.toView(mpa);
    }

    public void delete(Long id) {
        mpaStorage.getById(id).orElseThrow(MpaNotFoundException::new);
        mpaStorage.delete(id);
    }
}
