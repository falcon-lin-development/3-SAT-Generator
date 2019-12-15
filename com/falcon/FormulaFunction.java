package com.falcon;

/** 
 * Represents a function n different kinds of Formulas
 * @param <R> the type of the result of the function
 */

interface FormulaFunction<R>{
    
    /** @return this applied to Iden */
    public R onIdenVariable(IdenVariable Iden);
    
    /** @return this applied to var */
    public R onVariable(Variable var);
    
    /** @return this applied to not */
    public R onNot(Not not);
    
    /** @return this applied to And */
    public R onAnd(And and);
    
    /** @return this applied to Or */
    public R onOr(Or or);
    
    
}
