package com.example.sakilla.entities;

public enum Rating {
    G, PG, PG_13, R, NC_17;

    public static boolean isValid(String value) {
        for (Rating rating : values()) {
            if (rating.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}







