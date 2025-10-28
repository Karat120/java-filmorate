package ru.yandex.practicum.filmorate.util;

import org.springframework.stereotype.Component;

@Component
public class SequentialIdGenerator implements IdGenerator<Long> {
    private Long lastGeneratedID = 0L;
    
    public Long generate() {
        return ++lastGeneratedID;
    }
}
