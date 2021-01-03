package com.gonnect.ortool.demo;

import lombok.Data;

@Data
final public class ConstraintTerm {
    private final String name;
    private final Double coefficient;


    public ConstraintTerm(String term) {
        String[] termParts = term.split("\\*");
        this.name = termParts[1];
        this.coefficient = Double.valueOf(termParts[0]);
    }

    @Override
    public String toString() {
        return (coefficient > 0 ? "+" : "") + coefficient + " * " + name;
    }

}
