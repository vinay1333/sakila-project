package com.example.sakilla.entities;

public enum Rating {
    G, PG, PG_13, R, NC_17;

    @Override
    public String toString() {
        return name().replace('_', '-'); // Replace underscore with hyphen when converting to string
    }

    public static Rating fromString(String rating) {
        if (rating == null) {
            return null;
        }
        switch (rating) {
            case "PG-13":
                return PG_13;
            case "NC-17":
                return NC_17;
            default:
                try {
                    return Rating.valueOf(rating);
                } catch (IllegalArgumentException e) {
                    return null; // Or you can throw an exception if desired
                }
        }
    }
}






