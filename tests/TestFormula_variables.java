/**
 * Developer Notes(unrelated):
 * Exhaustive Cartesian coverage of partitions.
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.falcon.*;

import org.junit.jupiter.api.Test;

/**
 * @author falcon.lin.development@gmail.com
 * Testing strategy
 * 
 * Partition the inputs as follows:
 * actual types: Iden, Variable, Not, And, Or
 * number of Formula: 0, 1, 2, 3, 20
 *
 * 
 * Covers each part.
 */
class TestFormula_variables {
    
    // covers actual type: Iden
    //        no. of Form: 1
    @Test void testZeroVariable() {
        Formula test0 = new IdenVariable();
        Set<String> output = test0.variables();
        assertTrue(output.isEmpty());
    }
    
    // covers actual type Variable
    //        no. of Formula = 1
    @Test
    void testOneVariable() {
        Formula test1 = new Variable("x");
        Set<String> output = test1.variables();
        Set<String> expected = new HashSet<>();
        expected.add("x");
        assertEquals(expected, output);
        
    }
    
    // covers actual type Not
    //        no. of Formula = 2
    @Test
    void testTwoFormulaWithNot() {
        Formula test2 = new Not(new Variable("x"));
        Set<String> output = test2.variables();
        Set<String> expected = new HashSet<>();
        expected.add("x");
        assertEquals(expected, output);
    }
    
    // covers actual type And
    //        no. of Formula = 3
    @Test
    void testThreeFormula() {
        Formula test3 = new And(new Variable("x"), new Variable("y"));
        Set<String> output = test3.variables();
        Set<String> expected = new HashSet<>();
        expected.addAll(List.of("x", "y"));
        assertEquals(expected, output);
    }
    
    // covers actual type Or
    //        no. of Formula = 20
    // 
    // TestCase =
    // (  (  (v(x)&&v(y))  || (not(v(x)||x(z)))    ) || ( v(y)&&x(z) )  )  && 
    // not(not(not(     (v(y) && not(v(z))     ))))
    @Test
    void testTwentyFormulaWithNot() {
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
        Set<String> output = test4.variables();
        Set<String> expected = new HashSet<>();
        expected.addAll(List.of("x", "y", "z"));
        assertEquals(expected, output);
    }
    
    

}
