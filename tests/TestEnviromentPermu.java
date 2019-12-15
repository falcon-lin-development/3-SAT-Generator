package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import com.falcon.*;

import org.junit.jupiter.api.Test;


/**
 * Test Strategy
 * 
 * Partition:
 * no. of variables: 0, 1, 3
 * 
 * Covers each part
 */
class TestEnviromentPermu {
    
    // Covers: no. of variables: 0
    @Test
    void testZeroVaribales() {
        Set<String> input = new HashSet<String>();
        Environment test1 = new Environment(input);
        Set<Map<String, Boolean>> output = test1.environmentsPermu();
        assertTrue(output.isEmpty(), output.toString());
    }
    
    // Covers: no of variables: 1
    @Test void testOneVariables() {
        Set<String> input = new HashSet<String>();
        input.add("x");
        Environment test2 = new Environment(input);
        Set<Map<String, Boolean>> output = test2.environmentsPermu();
        assertEquals(2, output.size());
    }
    
    // Covers: no of variables: 3
    @Test void testThreeVariables() {
        Set<String> input = new HashSet<String>();
        input.addAll(List.of("x", "y", "z"));
        Environment test2 = new Environment(input);
        Set<Map<String, Boolean>> output = test2.environmentsPermu();
        assertEquals(8, output.size());
    }

}
