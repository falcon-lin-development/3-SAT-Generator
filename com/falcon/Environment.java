package com.falcon;

import java.util.*;

public class Environment {
    
    private Object[] vars;
    
    public Environment(Set<String> variables) {
        vars = variables.toArray();
        
    }
    
    public Set<Map<String, Boolean>> environmentsPermu(){
        Set<Map<String, Boolean>> out = new HashSet<>();
        
        if(this.vars.length == 0) {
            
        } else {
            for (int i = 0; i < (int)Math.pow(2, this.vars.length); i++) {
                Map<String, Boolean> thisEnvironment = new HashMap<>();
                String binary = Integer.toBinaryString(i);
                String paddedBinary = String.format("%"+vars.length+"s", binary).replace(' ', '0');
                for (int j = 0; j < vars.length; j++) {
                    Boolean variableRealisation = paddedBinary.charAt(j) == '1'? true : false;
                    thisEnvironment.put((String) vars[j], variableRealisation);
                }
                out.add(Collections.unmodifiableMap(thisEnvironment));
            }
            
        }
        
        return out;        
    }
}
