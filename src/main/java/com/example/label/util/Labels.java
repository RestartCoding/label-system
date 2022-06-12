package com.example.label.util;

import java.util.UUID;

public class Labels {

    public static String generateCode() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private Labels() {
        throw new AssertionError();
    }
}
