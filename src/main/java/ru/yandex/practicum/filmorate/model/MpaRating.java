package ru.yandex.practicum.filmorate.model;

public enum MpaRating {
    G,
    PG,
    PG_13("PG-13"),
    R,
    NC_17("NC-17");
    private final String dbValue;

    MpaRating(String dbValue) {
        this.dbValue = dbValue;
    }

    MpaRating() {
        this.dbValue = this.name();
    }

    public String getDbValue() {
        return dbValue;
    }

    public static MpaRating fromDbValue(String value) {
        for (MpaRating rating : values()) {
            if (rating.dbValue.equals(value)) return rating;
        }
        throw new IllegalArgumentException("Unknown MPA rating: " + value);
    }
}