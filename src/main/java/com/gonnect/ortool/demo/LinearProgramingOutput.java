package com.gonnect.ortool.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import lombok.Data;
import lombok.SneakyThrows;
import org.mvel2.MVEL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Data
public final class LinearProgramingOutput {

    private String solution;
    private Double objectiveFunctionValue;
    private final Map<String, String> solutionValue;
    private Map<String, Double> slacks;
    private Code code;

    public LinearProgramingOutput(LinearProgramming lp) {
        MPSolver solver = lp.getSolver();
        this.solution = solver.solve().name();
        this.objectiveFunctionValue = lp.getObjective().value();
        Map<String, MPVariable> mpVariableMap = lp.getMpVariableContainer().getMpVariableMap();
        this.solutionValue = new HashMap<>(mpVariableMap.size());
        mpVariableMap.forEach((name, value) -> {
            solutionValue.put(name, valueOf(Math.round(value.solutionValue())));
        });

        final Map<String, Slack> slacks = new HashMap<>();
        lp.getConstraints().forEach(constraintTermContainer -> {
            constraintTermContainer.getConstraintTerms().forEach(constraintTerm -> {
                String constraintName = constraintTermContainer.getName();
                if (!slacks.containsKey(constraintName)) {
                    slacks.put(constraintName, new Slack(constraintTermContainer.getValue()));
                }
                Double termEval = (Double) MVEL.eval(constraintTerm.toString().replace("+", ""), solutionValue);
                slacks.get(constraintName).addToRealValue((double) Math.round(termEval));
            });
        });

        this.slacks = slacks.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().slack()));
        this.code = new Code(lp);
    }

    @SneakyThrows
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }

    @Data
    private static class Slack {
        private final Double upperBoundValue;
        private Double realValue;

        public Slack(Double upperBoundValue) {
            this.upperBoundValue = upperBoundValue;
            this.realValue = Double.valueOf(0);
        }

        public void addToRealValue(Double value) {
            realValue = realValue + value;
        }

        public Double slack() {
            return (upperBoundValue - realValue);
        }

    }

    @Data
    static class Code {
        private final String solver;
        private final String variables;
        private final String objectiveFunction;
        private final List<String> constraints;

        public Code(LinearProgramming lp) {
            this.solver = lp.getType();
            this.variables = lp.getMpVariableContainer().toString().replace("variables:", "");
            this.objectiveFunction = lp.getObjectiveFunctionEnum().code() + " " + lp.getObjectiveFunction();
            this.constraints = new ArrayList<>();

            lp.getConstraints().forEach(constraint -> {
                constraints.add(constraint.toString());
            });
        }
    }
}
