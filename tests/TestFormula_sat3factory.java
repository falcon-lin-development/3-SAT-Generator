package tests;

import static org.junit.jupiter.api.Assertions.*;

import com.falcon.*;
import java.util.*;

import org.junit.jupiter.api.Test;


/**
 * Test Strategy
 * 
 * Partition:
 * Negation: with(out)
 * no. of blocks = 1, 7
 * no. of variables = 1,5,9
 * 
 * Covers each part
 */
class TestFormula_sat3factory {
    
    // Covers: no. of blocks = 1
    //         negation      = without
    //         no. of var    = 1
    @Test
    void testOneBlockOneVarWithoutNeg() {
        String input = "AAA";
        Formula test1 = Formula.Sat3Problem.sat3(input);
        
        assertEquals(LibraryMethods.singletonSet("A"), test1.variables());
        
        Map<String, Boolean> map = new HashMap<>();
        map.put("A", true);
        assertTrue(Formula.evaluate(test1, map));
        
        map.put("A", false);
        assertFalse(Formula.evaluate(test1, map));
        
        assertEquals(6, Formula.countVisits(test1));
    }
    
    
    // Covers: negation = with
    //         no. of blocks = 7
    //         no. of variables = 5
    @Test void testSevenBlockFiveVarWithNeg(){
        String input = "A~AA B~B~B ~C~C~C DD~D EEE DB~A CED";
        Formula test1 = Formula.Sat3Problem.sat3(input);
        
        assertEquals(5, test1.variables().size());
        
        Map<String, Boolean> map = new HashMap<>();
        map.put("A", true);
        map.put("B", true);
        map.put("C", false);
        map.put("D", true);
        map.put("E", true);
        assertTrue(Formula.evaluate(test1, map));
        
        map.put("A", true);
        map.put("B", false);
        map.put("C", true);
        map.put("D", false);
        map.put("E", true);
        assertFalse(Formula.evaluate(test1, map));
    }
    
    // Covers: negation = with
    //         no. of blocks = 7
    //         no. of variables = 9
    @Test void testSevenBlockNineVarWithNeg(){
        String input = "A~B~C ~DEF GH~I A~BE CDE ~H~IE ~A~C~D";
        Formula test1 = Formula.Sat3Problem.sat3(input);
        
        assertEquals(9, test1.variables().size());
        
        Map<String, Boolean> map = new HashMap<>();
        map.put("A", true);
        map.put("B", true);
        map.put("C", false);
        map.put("D", true);
        map.put("E", true);
        map.put("F", true);
        map.put("G", true);
        map.put("H", true);
        map.put("I", true);
        assertTrue(Formula.evaluate(test1, map));
        
        map.put("A", true);
        map.put("B", true);
        map.put("C", true);
        map.put("D", true);
        map.put("E", true);
        map.put("F", true);
        map.put("G", true);
        map.put("H", true);
        map.put("I", true);
        assertFalse(Formula.evaluate(test1, map));
    }
}






















