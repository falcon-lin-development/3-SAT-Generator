/**
 * @author falcon.lin.development@gmail.com
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import com.falcon.*;

/**
 * Test Strategy
 * 
 * Partition:
 * no. of formula = 0,1,2,3,20
 * no. of variables = 0, 1, 2, 3 <==> no. of env = 0, 2, 4, 8
 * 
 * Covers each part
 */
class TestFormula_evaluate{
    
    // Cover no. of formula = 0
    //       no. of environment = 0
    @Test void testZeroFormulaZeroVariable() {
        Formula test0 = new IdenVariable();
        Map<String, Boolean> map = new HashMap<>();
        Boolean output = Formula.evaluate(test0, map);
        assertTrue(output); // by default always true
    }
    
    // Cover no. of formula = 1
    //       no. of environment = 2
    @Test void testOneFormulaOneVariable(){
        Formula test1 = new Variable("x");
        Map<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        Boolean output = Formula.evaluate(test1, map);
        assertTrue(output);
        
        map.put("x", false);
        Boolean out2 = Formula.evaluate(test1, map);
        assertFalse(out2);
    }
    
    // Cover no. of formula = 2
    //       no. of env     = 2
    @Test void testTwoFormulaOneVariable(){
        Formula test2 = new Not(new Variable("x"));
        Map<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        Boolean output = Formula.evaluate(test2, map);
        assertFalse(output);
        
        map.put("x", false);
        Boolean out2 = Formula.evaluate(test2, map);
        assertTrue(out2);
        }
    
    // Covers no. of formula = 3
    //        no. of env     = 4
    @Test void testThreeFormulaTwoVariable() {
        Formula test3 = new And(new Variable("x"), new Variable("y"));
        Map<String, Boolean> map = new HashMap<>();
        map.put("x", true);
        map.put("y", true);
        Boolean output = Formula.evaluate(test3, map);
        assertTrue(output);
        
        map.put("x", true);
        map.put("y", false);
        Boolean out2 = Formula.evaluate(test3, map);
        assertFalse(out2);
        
        map.put("x", false);
        map.put("y", true);
        Boolean out3 = Formula.evaluate(test3, map);
        assertFalse(out3);
        
        map.put("x", false);
        map.put("y", false);
        Boolean out4 = Formula.evaluate(test3, map);
        assertFalse(out4);
    }
    
    // Covers no. of formula = /
    //        no. of env     = 8
    @Test void testThreeVariable() {
        Formula varX = new Variable("x");
        Formula varY = new Variable("y");
        Formula varZ = new Variable("z");
        Formula test4 = new Or(varX, new And(varY, varZ));
        Map<String, Boolean> map = new HashMap<>();
        
        // TTT
        map.put("x", true);
        map.put("y", true);
        map.put("z", true);
        Boolean output = Formula.evaluate(test4, map);
        assertTrue(output);
        
        // TTF
        map.put("x", true);
        map.put("y", true);
        map.put("z", false);
        Boolean out2 = Formula.evaluate(test4, map);
        assertTrue(out2);
        
        // TFT
        map.put("x", true);
        map.put("y", false);
        map.put("z", true);
        Boolean out3 = Formula.evaluate(test4, map);
        assertTrue(out3);
        
        
        // TFF
        map.put("x", true);
        map.put("y", false);
        map.put("z", false);
        Boolean out4 = Formula.evaluate(test4, map);
        assertTrue(out4);
        
        // FTT
        map.put("x", false);
        map.put("y", true);
        map.put("z", true);
        Boolean out5 = Formula.evaluate(test4, map);
        assertTrue(out5);
        
        // FTF
        map.put("x", false);
        map.put("y", true);
        map.put("z", false);
        Boolean out6 = Formula.evaluate(test4, map);
        assertFalse(out6);
        
        
        // FFT
        map.put("x", false);
        map.put("y", false);
        map.put("z", true);
        Boolean out7 = Formula.evaluate(test4, map);
        assertFalse(out7);
        
        // FFF
        map.put("x", false);
        map.put("y", false);
        map.put("z", false);
        Boolean out8 = Formula.evaluate(test4, map);
        assertFalse(out8);
    }
    
    // Covers no. of Formula = 20
    //        no. of env     = 8
    @Test void test20Formula3Variable(){
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
        Map<String, Boolean> map = new HashMap<>();
        
        // TTT
        map.put("x", true);
        map.put("y", true);
        map.put("z", true);
        Boolean output = Formula.evaluate(test5, map);
        assertTrue(output);
        
        // TTF
        map.put("x", true);
        map.put("y", true);
        map.put("z", false);
        Boolean out2 = Formula.evaluate(test5, map);
        assertFalse(out2);
        
        // TFT
        map.put("x", true);
        map.put("y", false);
        map.put("z", true);
        Boolean out3 = Formula.evaluate(test5, map);
        assertFalse(out3);
        
        
        // TFF
        map.put("x", true);
        map.put("y", false);
        map.put("z", false);
        Boolean out4 = Formula.evaluate(test5, map);
        assertFalse(out4);
        
        // FTT
        map.put("x", false);
        map.put("y", true);
        map.put("z", true);
        Boolean out5 = Formula.evaluate(test5, map);
        assertTrue(out5);
        
        // FTF
        map.put("x", false);
        map.put("y", true);
        map.put("z", false);
        Boolean out6 = Formula.evaluate(test5, map);
        assertFalse(out6);
        
        
        // FFT
        map.put("x", false);
        map.put("y", false);
        map.put("z", true);
        Boolean out7 = Formula.evaluate(test5, map);
        assertFalse(out7);
        
        // FFF
        map.put("x", false);
        map.put("y", false);
        map.put("z", false);
        Boolean out8 = Formula.evaluate(test5, map);
        assertTrue(out8);
    }
}




































