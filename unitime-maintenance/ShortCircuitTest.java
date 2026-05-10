import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class ShortCircuitTest {
    
    // Helper class for testing method invocation
    static class CreditType {
        boolean isValid() { 
            return true; 
        }
    }
    
    // Method using non-short-circuit operator & (incorrect for null safety)
    public static boolean checkWithAnd(CreditType cct) {
        return cct != null & cct.isValid();
    }
    
    // Method using short-circuit operator && (correct for null safety)
    public static boolean checkWithAndAnd(CreditType cct) {
        return cct != null && cct.isValid();
    }
    
    @Test
    @DisplayName("Test with & operator - Should throw NullPointerException")
    public void testWithNonShortCircuit_Operator() {
        CreditType cct = null;
        
        // This should throw NullPointerException because & evaluates both sides
        assertThrows(NullPointerException.class, () -> {
            checkWithAnd(cct);
        }, "Expected NullPointerException when using & with null object");
    }
    
    @Test
    @DisplayName("Test with && operator - Should NOT throw exception")
    public void testWithShortCircuit_Operator() {
        CreditType cct = null;
        
        // This should not throw exception because && uses short-circuit evaluation
        assertDoesNotThrow(() -> {
            boolean result = checkWithAndAnd(cct);
            assertFalse(result, "Expected false when object is null");
        }, "Should not throw exception with && operator");
    }
    
    @Test
    @DisplayName("Test with && operator - Valid object should return true")
    public void testWithShortCircuit_ValidObject() {
        CreditType cct = new CreditType();
        
        boolean result = checkWithAndAnd(cct);
        
        assertTrue(result, "Expected true when object is valid");
    }
    
    @Test
    @DisplayName("Performance Test: && should evaluate fewer times")
    public void testEvaluationCount() {
        // Test that && stops evaluation after the first condition if false
        EvaluationCounter counter = new EvaluationCounter();
        
        // With &&: isValid() should not be called when isNull() is false
        boolean result1 = (counter.isNull() && counter.isValid());
        assertEquals(0, counter.getValidCallCount(), 
            "isValid() should not be called when isNull() is false");
        
        // Reset counter for next test
        counter = new EvaluationCounter();
        
        // With &: isValid() should be called even when first condition is false
        boolean result2 = (counter.isNull() & counter.isValid());
        assertEquals(1, counter.getValidCallCount(), 
            "isValid() should be called even when first condition is false");
    }
    
    // Helper class for counting method invocations
    static class EvaluationCounter {
        private int validCallCount = 0;
        
        boolean isNull() {
            return false; // Simulates a condition that evaluates to false
        }
        
        boolean isValid() {
            validCallCount++;
            return true;
        }
        
        int getValidCallCount() {
            return validCallCount;
        }
    }
}