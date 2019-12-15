package com.falcon;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Formula{
    
    public interface Visitor<R>{
        public R on(IdenVariable Iden);
        public R on(Variable var);
        public R on(Not not);
        public R on(And and);
        public R on(Or or);

    }
    /**
     * 
     */
    private static <R> Visitor<R> makeVisitor(
            Function<IdenVariable, R> onIdenVariable,
            Function<Variable, R> onVariable,
            Function<Not, R> onNot,
            Function<And, R> onAnd,
            Function<Or, R> onOr
            ){
        return new Visitor<R>() {
            @Override
            public R on(IdenVariable Iden) {
                return onIdenVariable.apply(Iden);
            }
            
            @Override
            public R on(Variable var) {
                return onVariable.apply(var);
            }

            @Override
            public R on(Not not) {
                return onNot.apply(not);
            }

            @Override
            public R on(And and) {
                return onAnd.apply(and);
            }

            @Override
            public R on(Or or) {
                return onOr.apply(or);
            }
        };
    }
        
    public <R> R accept(Visitor<R> visitor);
    
    /** @return all the variables that appear in this Formula */
    public Set<String> variables();
    
//    /** @return whether the expression is satisfiable */
//    public Boolean satisfiable() {}
    
    /** 
     * Call a function on this Formula
     * @param <R> the type of the result
     * @param function the function to call
     * @return function applied to this
     */
    public <R> R callFunction(FormulaFunction<R> function);
    
    /**
     * @param formula
     * @param map must have a key for every variable in formula
     * @return the value of the formula with variables substitued with their
     * values in the map
     */
    public static boolean evaluate(Formula formula, Map<String, Boolean> map) {
        return formula.accept(makeVisitor(
                (IdenVariable Iden) -> true,
                (Variable var) -> map.get(var.name()),
                (Not not) -> !evaluate(not.formula(), map),
                (And and) -> evaluate(and.left(), map) && evaluate(and.right(), map),
                (Or or) -> evaluate(or.left(), map) || evaluate(or.right(), map)
                ));
    }
    
    /**
     * @param formula
     * @return all the variables that appear in this Formula
     */
    public static Set<String> variables(Formula formula){
        return formula.accept(makeVisitor(
                (IdenVariable Iden) -> LibraryMethods.EmptySet(),
                (Variable var) -> LibraryMethods.singletonSet(var.name()),
                (Not not) -> variables(not.formula()),
                (And and) -> LibraryMethods.setUnion(variables(and.left()), variables(and.right())),
                (Or or) -> LibraryMethods.setUnion(variables(or.left()), variables(or.right())) 
                ));
    }
    
    /**
     * @param formula
     * @return number of visits through one recursive call.
     */
    public static int countVisits(Formula formula) {
        
        return formula.accept(makeVisitor(
                (IdenVariable Iden) -> 0,
                (Variable var) -> 1,
                (Not not) -> 1 + countVisits(not.formula()),
                (And and) -> 1 + countVisits(and.left()) + countVisits(and.right()),
                (Or or) -> 1 + countVisits(or.left()) + countVisits(or.right())

                ));
    }
    
    /** Convert a Formula to a String representation. */
    public static String toString(Formula formula) {
        return formula.accept(makeVisitor(
                (IdenVariable Iden) -> "",
                (Variable var) -> var.name(),
                (Not not) -> "!" + toString(not.formula()),
                (And and) -> "(" + toString(and.left()) + " && " + toString(and.right()) + ")",
                (Or or) -> "(" + toString(or.left()) + " || " + toString(or.right()) + ")"
                ));
    }
        
    /**
     * @param variables
     * @return Set of maps of all possible environments
     */
    public static Set<Map<String, Boolean>> environments(Set<String> variables){
        Environment output = new Environment(variables);
        return output.environmentsPermu();
    }
    
    /** @return whether the expression is satisfiable */
    public static Boolean satisfiable(Formula formula) {
        for(Map<String, Boolean> env: environments(variables(formula))) {
            if(evaluate(formula, env)) {
                return true;
            }     
        }
        return false;
    }
    
    /**
     * factory class for 3-SAT category Formula
     */
    public static class Sat3Problem{

        // Factory Method
        /**@Return a random blocks of random numbers of sat-3 structured formula*/
        public static Formula sat3problem() {
            return sat3(random3sat(1, 9, 1, 9));
        }
        
        /**
         * 
         * @param noOfVar requires 1-9 inclusive
         * @param noOfBlocks requires >0
         * @return a fixed no. of blocks of fixed no. of vaiables of sat-3 structured formula
         */
        public static Formula sat3problem(int noOfVar, int noOfBlocks) {
            return sat3(random3sat(noOfVar, noOfVar, noOfBlocks, noOfBlocks));
        }
        
        /**
         * 
         * @param minNoOfVar requires >0
         * @param maxNoOfVar requires <9
         * @param minNoOfBlocks requires >0
         * @param maxNoOfBlocks 
         * @return a random generated 3-SAT formula with specified constraints
         */
        public static Formula sat3problem(int minNoOfVar, int maxNoOfVar, int minNoOfBlocks,int maxNoOfBlocks) {
            return sat3(random3sat(minNoOfVar, maxNoOfVar, minNoOfBlocks, maxNoOfBlocks));            
        }
        
        /**
         * Generate a random String of 3-sat grammered string.
         * @param minNoOfVar requires >0
         * @param maxNoOfVar requires <9
         * @param minNoOfBlocks requires >0
         * @param maxNoOfBlocks 
         * @return an random valid 3_SAT input for sat3 to parse
         */
        private static String random3sat(int minNoOfVar, int maxNoOfVar, int minNoOfBlocks,
                int maxNoOfBlocks
                ) {
            int numberOfVariables = (int) Math.floor(Math.random() * (maxNoOfVar - minNoOfVar + 1) + minNoOfVar);
            int numberOfBlocks = (int) Math.floor(Math.random() * (maxNoOfBlocks - minNoOfBlocks + 1) + minNoOfBlocks);
            String vars = "ABCDEFGHI";
            
            String out = "";
            for (int i = 0 ; i < numberOfBlocks ;i++) {
                out = out + " " + oneRandomBlock(vars.substring(0, numberOfVariables).toCharArray());
            }
            return out;
        }
        
        /** 
         * @param varsArray 
         * @return one random block using the variables in the given varsArray 
         */
        private static String oneRandomBlock(char[] varsArray) {
            String out = "";
            for(int i = 0; i < 3; i++) {
                Boolean isPos = Math.random() > 0.5;
                int varIndex = (int) (Math.random() * (varsArray.length));
                if(isPos)
                    out = out + varsArray[varIndex];
                else if (!isPos)
                    out = out + "~" + varsArray[varIndex];
                else
                    throw new RuntimeException("Invalid");
            }
            return out;
        }
 
        /**
         * Make Formula from a string usin a variant of xyz notation
         * 
         * <p>The notation consists of whitespace-delimited symbols represnting
         * one variable. The vertical bar | may be used as a delimiter
         * for measures; 3_SAT() treats it as a space
         * Grammer:
         * <pre>
         *      3_SAT ::= block+
         *      block ::= variable variable variable
         *      variable ::= '~'? [ABCDEFGHI]
         * </pre>
         * <p> Examples
         *     ABC              (A||B||C)
         *     A~B~C BB~A       (A||~B||~C)&&(B||B||~A)
         * 
         * @param sat3 String of blocks in simplified xyz notaton given above
         * @return 3-SAT Structured Formula
         */
        public static Formula sat3(String sat3) {
            Formula sat3problem = new IdenVariable();
            for(String block: sat3.strip().split("[\\s|]+")) {
                if(!block.isEmpty()){
                    sat3problem = new And(sat3problem, parseBlock(block));
                }
            }
            return sat3problem;
        }
        
        /** Parse a block into a block of Variables. */
        private static Formula parseBlock(String block) {        
            Matcher m = Pattern.compile("(?<v1>~?[A-I])(?<v2>~?[A-I])(?<v3>~?[A-I])").matcher(block);
            
            if(!m.matches()) throw new IllegalArgumentException("couldn;t understand " + block);
            
            String v1 = m.group("v1");
            String v2 = m.group("v2");
            String v3 = m.group("v3");
            return new Or(new Or(parseFormula(v1), parseFormula(v2)), parseFormula(v3));
        }
        
        /** Parse a symbol into a Formula. */
        private static Formula parseFormula(String symbol) {
            if(symbol.length() == 2) {
                return new Not(parseFormula(symbol.substring(1)));
            } else if (symbol.length() == 1) {
                return new Variable(symbol);
            } else {
                throw new RuntimeException("Illegal input " + symbol);
            }
        }
    
    }
    
}
 
    
    
    
    
    
    
    
    
    
    
 



















    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
