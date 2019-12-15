package tests;

import static org.junit.jupiter.api.Assertions.*;

import com.falcon.*;
import java.util.*;

import org.junit.jupiter.api.Test;

/**
 * Test Strategy
 * @author alohahawk
 *
 * Partition
 * sat: yes/no
 * 3-sat-CNF: yes/no
 * no. of variables = 1, 5, 9
 * no. of blocks = 1, 4, 9
 * no. of formulas = 1, 4, 18
 */
class TestFormula_satisfiable {
    
    // Covers: 
    // satisfiable = yes
    // 3-sat-CNF   = no
    // no. of variable = 1
    // no. of block = /
    // no. of formulas = 1
    @Test
    void testOneVariableOneFormula() {
        Formula test = new Variable("A");
        assertTrue(Formula.satisfiable(test));
    }
    
    // Covers:
    // sat = no
    // 3-sat-CNF   = no
    // no. of variable = 1
    // no. of block = /
    // no. of formulas = 4
    @Test void testOneVariableFourFormula() {
        Formula test = new And(new Variable("A"), new Not(new Variable("A")));
        assertFalse(Formula.satisfiable(test));
    }
    
    // Covers:
    // sat = yes
    // 3-sat-CNF = yes
    // no. of variable = 1
    // no. of block = 1
    // no. of formulas = /
    @Test void testOneVariableOneBlockCNF(){
        Formula test = Formula.Sat3Problem.sat3("AAA");
        assertTrue(Formula.satisfiable(test));
    }
    
    // Covers:
    // sat = yes
    // 3-sat-CNF   = yes
    // no. of vaiable = 5
    // no. of blocks = 3
    // no. of formulas = 18
    @Test void testFiveVariableThreeBlockTwentyFormulaCNF() {
        Formula test = Formula.Sat3Problem.sat3("ABC BCD EBA");
        assertEquals(18, Formula.countVisits(test), ()->Formula.toString(test));
        assertTrue(Formula.satisfiable(test));
    }
    
    // Covers:
    // sat = No
    // 3-sat-CNF = yes
    // no. of variable = 9
    // no. of block = 9
    // no. of formulas = /
    @Test void testNineVariableNineBlockCNF() {
        Formula test = Formula.Sat3Problem.sat3(
                "AB~C D~EF GH~I A~BI ~B~DE B~CH HGA AAA ~A~A~A");
        assertEquals(9, Formula.variables(test).size());
        assertFalse(Formula.satisfiable(test));
    }
}






























