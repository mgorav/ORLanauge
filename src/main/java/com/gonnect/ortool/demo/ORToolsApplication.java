package com.gonnect.ortool.demo;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.google.ortools.Loader.loadNativeLibraries;

@SpringBootApplication
public class ORToolsApplication {

    public static void main(String[] args) {
        loadNativeLibraries();
        SpringApplication.run(ORToolsApplication.class, args);
    }


}
