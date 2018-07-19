package com.android.flickerapp.utils;

public enum ImageSize {

    MEDIUM,
    LARGE;

    public String getValue() {
        if (this == MEDIUM) return "z";
        return "h";
    }

}