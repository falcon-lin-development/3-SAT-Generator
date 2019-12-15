package com.falcon;

import java.util.Set;

public class Variable implements Formula{
    private final String name;
    
    // constuctor
    public Variable(String name) {
        this.name = name;
    }
    
    // observer method
    public String name() {
        return this.name;
    }

    @Override
    public Set<String> variables() {
        return LibraryMethods.singletonSet(this.name);
    }

    @Override
    public <R> R callFunction(FormulaFunction<R> function) {
        return function.onVariable(this);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.on(this);
    }
    
}