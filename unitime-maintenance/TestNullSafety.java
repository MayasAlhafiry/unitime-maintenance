public class TestNullSafety {
    
    static class CreditType {
        boolean isValid() { 
            System.out.println("isValid() called");
            return true; 
        }
    }
    
    public static void main(String[] args) {
        CreditType cct = null;
        CreditType ccut = new CreditType();
        
        System.out.println("=== Testing with & (WRONG - causes crash) ===");
        try {
            
            if (cct != null & cct.isValid()) {
                System.out.println("SUCCESS: Both are valid");
            } else {
                System.out.println("FAILED: Validation failed");
            }
        } catch (NullPointerException e) {
            System.out.println(" ERROR: NullPointerException occurred!");
            System.out.println("   Message: " + e.getMessage());
        }
        
        System.out.println("\n=== Testing with && (CORRECT - safe) ===");
        try {
           
            
            if (ccut != null && ccut.isValid()) {
                System.out.println("SUCCESS: Both are valid");
            } else {
                System.out.println("FAILED: Validation failed");
            }
        } catch (NullPointerException e) {
            System.out.println("ERROR: NullPointerException occurred!");
        }
        
        System.out.println("\n=== Summary ===");
        System.out.println("Test with &:  FAILED (NullPointerException)");
        System.out.println("Test with &&: PASSED (No error)");
    }
}