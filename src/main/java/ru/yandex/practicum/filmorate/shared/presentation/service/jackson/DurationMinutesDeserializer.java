package ru.yandex.practicum.filmorate.shared.presentation.service.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Duration;

public class DurationMinutesDeserializer extends StdDeserializer<Duration> {

    public DurationMinutesDeserializer() {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        long minutes = p.getLongValue();
        return Duration.ofMinutes(minutes);
    }
}
