package com.falcon;

import java.util.Set;

public class Or implements Formula{
    private final Formula left, right;
    
    // constructor
    public Or(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }
    
    // observer
    public Formula left() {
        return this.left;
    }
    
    public Formula right() {
        return this.right;
    }

    @Override
    public Set<String> variables() {
        return LibraryMethods.setUnion(left.variables(), right.variables());
    }
    @Override
    public <R> R callFunction(FormulaFunction<R> function) {
        return function.onOr(this);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.on(this);
    } 
    
    
}