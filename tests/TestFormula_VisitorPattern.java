package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import com.falcon.*;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * @author falcon.lin.development@gmail.com
 * Test Strategy
 * 
 * Partition the input as follows:
 * number of formulas    : 0,1,2,3,20
 * functions to be called: VariablesInFormula, CountVisits
 * 
 * Covers each part
 * 
 */
@RunWith(JUnitPlatform.class)
class TestFormula_VisitorPattern {
    
    // Covers: no. of formulas = 0
    //         function        = VariablesInFormula, CountVisits
    @Test void testZeroFumulaOfTwoFunctions(){
        Formula test0 = new IdenVariable();
        Set<String> output = Formula.variables(test0);
        assertTrue(output.isEmpty());
        
        int output2 = Formula.countVisits(test0);
        assertEquals(0, output2);
    }
    
    // Covers: no. of formulas = 1
    //         function        = VariablesInFormula
    @Test
    void testOneFormulaOfVariablesInFormula() {
        Formula test1 = new Variable("x");
        Set<String> output = Formula.variables(test1);
        Set<String> expected = new HashSet<>();
        expected.add("x");
        assertEquals(expected, output);   
    }
    
    // Covers: no. of formulas = 2
    //         function        = CountVisits
    @Test void testTwoFormulaOfCountVisits() {
        Formula test2 = new Not(new Variable("x"));
        int output = Formula.countVisits(test2);
        int expected = 2;
        assertEquals(expected, output);
    }
    
    // Covers: no. of formulas = 3
    //         function        = VariablesInFormula
    @Test void testThreeFourmulaOfVariablesInFormula() {
        Formula test3 = new And(new Variable("x"), new Variable("y"));
        Set<String> output = Formula.variables(test3);
        Set<String> expected = new HashSet<>();
        expected.addAll(List.of("x", "y"));
        assertEquals(expected, output);
    }
    
    // Covers: no. of formulas = 20
    //         function        = CountVisits
    @Test void testTwentyFormulaOfCountVisits() {
        Formula varX = new Variable("x");
        Formula varY = new Variable("y");
        Formula varZ = new Variable("z");
        
        Formula leftMost = new And(varX, varY);
        Formula left2 = new Not(new Or(varX, varZ));
        Formula left3 = new And(varY, varZ);
        Formula left = new Or(new Or(leftMost, left2), left3);
        
        Formula rightInner = new And(varY, new Not(varZ));
        Formula right = new Not(new Not(new Not(rightInner)));
        
        Formula test4 = new And(left, right);
        int output = Formula.countVisits(test4);
        int expected = 20;
        assertEquals(output, expected);
    }
    
    // Covers: no. of formulas = 20
    //         function        = VariablesInFormula
    @Test void testTwentyFormulaOfVariablesInFormula() {
        Formula varX = new Variable("x");
        Formula varY = new Variable("y");
        Formula varZ = new Variable("z");
        
        Formula leftMost = new And(varX, varY);
        Formula left2 = new Not(new Or(varX, varZ));
        Formula left3 = new And(varY, varZ);
        Formula left = new Or(new Or(leftMost, left2), left3);
        
        Formula rightInner = new And(varY, new Not(varZ));
        Formula right = new Not(new Not(new Not(rightInner)));
        
        Formula test5 = new And(left, right);
        Set<String> output = Formula.variables(test5);
        Set<String> expected = new HashSet<>();
        expected.addAll(List.of("x", "y", "z"));
        assertEquals(expected, output);
    }
    
    
}



