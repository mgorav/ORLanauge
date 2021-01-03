package com.gonnect.ortool.demo;

import lombok.Data;

import static java.lang.Double.valueOf;

@Data
final public class ObjectiveFunctionTerm {
    private final String name;
    private final Double coefficient;
    private final ObjectiveFunctionEnum objectiveFunctionEnum;

    public ObjectiveFunctionTerm(String term, String objectiveFunctionType) {
        this.objectiveFunctionEnum = ObjectiveFunctionEnum.fromCode(objectiveFunctionType);
        String[] termParts = term.split("\\*");
        this.name = termParts[1];
        this.coefficient = valueOf(termParts[0]);
    }

    @Override
    public String toString() {
        return (coefficient > 0 ? "+" : " ") + coefficient + " * " + name;
    }

}
