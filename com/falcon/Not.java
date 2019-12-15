package com.falcon;

import java.util.Set;

import com.falcon.Formula.Visitor;

public class Not implements Formula{
    private final Formula formula;
    
    // constructor
    public Not(Formula formula) {
        this.formula = formula;
    }
    
    // observer
    public Formula formula() {
        return this.formula;
    }
    
    @Override
    public Set<String> variables() {
        return this.formula.variables();
    }
    @Override
    public <R> R callFunction(FormulaFunction<R> function) {
        return function.onNot(this);
    }
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.on(this);
    } 
    
       
}