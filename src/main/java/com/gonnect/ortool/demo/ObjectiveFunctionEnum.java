package com.gonnect.ortool.demo;

import java.util.HashMap;
import java.util.Map;

public enum ObjectiveFunctionEnum {
    MAXIMIZE("maximize:"),
    MINIMIZE("minimize:");
    private final String code;
    private static Map<String, ObjectiveFunctionEnum> cache = new HashMap<>();

    ObjectiveFunctionEnum(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

    public static ObjectiveFunctionEnum fromCode(String code) {
        if (cache.containsKey(code)) {
            return cache.get(code);
        }
        for (ObjectiveFunctionEnum element : values()) {
            if (element.code().equals(code)) {
                cache.put(code, element);
                return element;
            }
        }
        return null;
    }
}
