package com.rockbass2560.megacode.models;

import java.util.Locale;

public enum Tags {
    FEED,
    PERFIL,
    PROGRESO,
    JUGAR;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.getDefault());
    }
}
