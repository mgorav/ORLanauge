package com.gonnect.ortool.demo;

import com.google.ortools.linearsolver.MPVariable;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class MPVariableContainer {
    private final Map<String, MPVariable> mpVariableMap;

    public MPVariableContainer() {
        mpVariableMap = new HashMap<>();
    }

    public void addMPVariable(MPVariable mpVariable) {
        mpVariableMap.put(mpVariable.name(), mpVariable);
    }

    public MPVariable getMPVariableByName(String name) {
        return mpVariableMap.get(name);
    }

    @Override
    public String toString() {
        return "variables: " + StringUtils.collectionToCommaDelimitedString(mpVariableMap.keySet());
    }
}
