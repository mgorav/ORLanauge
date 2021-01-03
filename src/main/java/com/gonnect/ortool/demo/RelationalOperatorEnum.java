package com.gonnect.ortool.demo;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public enum RelationalOperatorEnum {
    gt(">", POSITIVE_INFINITY),
    gtEq(">=", POSITIVE_INFINITY),
    lt("<", NEGATIVE_INFINITY),
    ltEq("<=", NEGATIVE_INFINITY);

    private final String sign;
    private final Double infinity;
    private static Map<String, RelationalOperatorEnum> cache = new HashMap<>();

    RelationalOperatorEnum(String sign, Double infinity) {
        this.sign = sign;
        this.infinity = infinity;
    }

    public String sign() {
        return this.sign;
    }

    public Double infinity() {
        return this.infinity;
    }

    public static RelationalOperatorEnum fromCode(String sign) {
        if (cache.containsKey(sign)) {
            return cache.get(sign);
        }
        for (RelationalOperatorEnum element : values()) {
            if (element.sign.equals(sign)) {
                cache.put(sign, element);
                return element;
            }
        }
        return null;
    }

}
