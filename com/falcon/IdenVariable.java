package com.falcon;

import java.util.Set;

public class IdenVariable implements Formula{
    private final String name = "Iden";
    
 // constuctor
    public IdenVariable() {}
    
    // observer method
    public String name() {
        return this.name;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.on(this);
    }

    @Override
    public Set<String> variables() {
        return LibraryMethods.EmptySet();
    }

    @Override
    public <R> R callFunction(FormulaFunction<R> function) {
        // TODO Auto-generated method stub
        return function.onIdenVariable(this);
    }

}
