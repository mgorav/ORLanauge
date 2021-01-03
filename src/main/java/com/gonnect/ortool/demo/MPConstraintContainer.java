package com.gonnect.ortool.demo;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPVariable;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public final class MPConstraintContainer {
    private final Map<String, MPConstraint> mpConstraintMap;

    public MPConstraintContainer() {
        this.mpConstraintMap = new HashMap<>();
    }

    public void addMPConstraint(MPConstraint mapMpConstraint) {
        this.mpConstraintMap.put(mapMpConstraint.name(), mapMpConstraint);
    }

    public MPConstraint getMPConstraintByName(String name) {
        return this.mpConstraintMap.get(name);
    }

    @Override
    public String toString() {
        return "variables: " + StringUtils.collectionToCommaDelimitedString(mpConstraintMap.keySet());
    }
}
