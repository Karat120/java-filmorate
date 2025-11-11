package ru.yandex.practicum.filmorate.shared.application.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.shared.domain.service.IdGenerator;

@Component
@Scope("prototype")
public class SequentialIdGenerator implements IdGenerator<Long> {
    private Long lastGeneratedID = 0L;

    public Long generate() {
        return ++lastGeneratedID;
    }
}
