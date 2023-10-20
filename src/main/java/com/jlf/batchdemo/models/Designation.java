package com.jlf.batchdemo.models;

import com.jlf.batchdemo.exceptions.EntityNotFoundException;

import java.util.Arrays;

public enum Designation {
    DEVELOPER("dev", "Developer"),
    DESIGNER("des", "Designer"),
    TESTER("test", "Tester");

    private final String code;
    private final String title;

    // Constructor
    Designation(String code, String title) {
        this.code = code;
        this.title = title;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public static Designation getByCode(final String code) throws EntityNotFoundException {
        return Arrays.stream(Designation.values())
                .filter(designation -> designation.getCode().equals(code))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }
}
