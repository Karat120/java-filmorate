package ru.yandex.practicum.filmorate.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SequentialIdGenerator implements IdGenerator<Long> {
    private Long lastGeneratedID = 0L;

    public Long generate() {
        return ++lastGeneratedID;
    }
}
