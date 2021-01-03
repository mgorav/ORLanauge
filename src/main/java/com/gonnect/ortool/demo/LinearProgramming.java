package com.gonnect.ortool.demo;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import lombok.Data;

import java.util.List;

import static com.gonnect.ortool.demo.ObjectiveFunctionEnum.MINIMIZE;
import static com.google.ortools.Loader.loadNativeLibraries;
import static java.lang.Double.POSITIVE_INFINITY;

@Data
final public class LinearProgramming {

    private final String type;
    private final MPSolver solver;
    private final MPVariableContainer mpVariableContainer;
    private final MPConstraintContainer mpConstraintContainer;
    private final double infinity;
    private final MPObjective objective;
    private String objectiveFunction = "";
    private ObjectiveFunctionEnum objectiveFunctionEnum;
    private List<ConstraintTermContainer> constraints;

    public LinearProgramming(String type) {
        loadNativeLibraries();
        this.type = type;
        solver = MPSolver.createSolver(type);
        if (solver == null) {
            throw new RuntimeException("Could not create solver " + type);
        }
        mpVariableContainer = new MPVariableContainer();
        infinity = POSITIVE_INFINITY;
        mpConstraintContainer = new MPConstraintContainer();
        objective = solver.objective();
    }

    public void addMPVariable(String name) {
        mpVariableContainer.addMPVariable(solver.makeNumVar(0.0, infinity, name));
    }

    public void setConstraints(List<ConstraintTermContainer> constraints) {
        this.constraints = constraints;
        constraints.forEach(c -> {
            String name = c.getName();
            RelationalOperatorEnum relationalOperatorEnum = c.getRelationalOperatorEnum();
            List<ConstraintTerm> constraintTerms = c.getConstraintTerms();
            Double value = c.getValue();
            final MPConstraint mpConstraint = solver.makeConstraint(relationalOperatorEnum.infinity(), value, name);

            constraintTerms.forEach(constraintTerm -> {
                mpConstraint.setCoefficient(mpVariableContainer.getMPVariableByName(constraintTerm.getName()), constraintTerm.getCoefficient());
            });

            mpConstraintContainer.addMPConstraint(mpConstraint);
        });

    }

    public void setObjectiveFunction(List<ObjectiveFunctionTerm> objectiveFunctionTerms) {
        objectiveFunctionTerms.forEach(objectiveFunctionTerm -> {
            addMPVariable(objectiveFunctionTerm.getName());
            addObjectiveFunctionCoefficients(objectiveFunctionTerm);
            if (objectiveFunctionEnum == null) {
                objectiveFunctionEnum = objectiveFunctionTerm.getObjectiveFunctionEnum();
                if (objectiveFunctionEnum == MINIMIZE) {
                    objective.setMinimization();
                } else {
                    objective.setMaximization();
                }
            }
        });

    }

    public LinearProgramingOutput solve() {
        check();
        return new LinearProgramingOutput(this);
    }

    public void check() {
        this.constraints.forEach(constraint -> {
            constraint.getConstraintTerms().forEach(constraintTerm -> {
                if (!mpVariableContainer.getMpVariableMap().containsKey(constraintTerm.getName())) {
                    throw new RuntimeException("Variable " + constraintTerm.getName() + " no declared!");
                }
            });
        });
    }

    private void addObjectiveFunctionCoefficients(ObjectiveFunctionTerm objectiveFunctionTerm) {
        objectiveFunction = objectiveFunction + objectiveFunctionTerm;
        objective.setCoefficient(mpVariableContainer.getMPVariableByName(objectiveFunctionTerm.getName()), objectiveFunctionTerm.getCoefficient());
    }

    @Override
    public String toString() {
        return "solver: " + type + "\n" + mpVariableContainer + " \n" + this.objectiveFunctionEnum.code() + " " + objectiveFunction + " \n" + toStringConstraints();
    }

    public String toStringConstraints() {
        StringBuilder constraintBuilder = new StringBuilder("constraints: \n");
        this.constraints.forEach(constraint -> {
            constraintBuilder.append("\t" + constraint + "\n");
        });
        return constraintBuilder.toString();
    }
}
