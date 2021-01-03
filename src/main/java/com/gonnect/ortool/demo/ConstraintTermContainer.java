package com.gonnect.ortool.demo;

import lombok.Data;

import java.util.List;

@Data
final public class ConstraintTermContainer {
    private final String name;
    private final List<ConstraintTerm> constraintTerms;
    private final RelationalOperatorEnum relationalOperatorEnum;
    private final Double value;

    public ConstraintTermContainer(String name, List<ConstraintTerm> constraintTerms,
                                   String constraintLogicalOperatorStr, String value) {
        this.name = name;
        this.constraintTerms = constraintTerms;
        this.relationalOperatorEnum = RelationalOperatorEnum.fromCode(constraintLogicalOperatorStr);
        this.value = Double.valueOf(value);
    }


    @Override
    public String toString() {
        return name + ": " + getConstraintEqn() + relationalOperatorEnum.sign() + " " + value;
    }

    public String getConstraintEqn() {
        final StringBuilder constraint = new StringBuilder();
        constraintTerms.forEach(constraintTerm -> {
            constraint.append(constraintTerm.toString() + " ");
        });
        return constraint.toString();
    }
}
