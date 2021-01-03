package com.gonnect.ortool.demo;


public class ORParserExecutor {
    public static void main(String[] args) {
        String gtc = "solver: GLOP\n" +
                "variables: wrenches,pliers\n" +
                "maximize: 0.13 * wrenches + 0.10 * pliers\n" +
                "constraints:\n" +
                "steel: 1.5 * wrenches + 1 * pliers <= 27000\n" +
                "molding: 1.0 * wrenches + 1 * pliers <= 21000\n" +
                "assembly: 0.3 * wrenches + 0.5 * pliers <= 9000\n" +
                "demand1: 1 * wrenches <= 15000\n" +
                "demand2: 1 * pliers <= 16000";
        LinearProgramming lp = ORParser.parse(gtc.replace(" ", ""));
        lp.check();
        System.out.println(lp.toString());
        LinearProgramingOutput output = lp.solve();
        System.out.println(output);
        System.out.println("Done");
    }

    public static LinearProgramingOutput parse(LinearProgramingInput input) {
        LinearProgramming lp = ORParser.parse(input.getCode().replace(" ", ""));
        lp.check();
        return lp.solve();
    }
}
