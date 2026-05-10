public class TestShortCircuit {

    static int evaluationCount = 0;

    static boolean checkWithSideEffect(String name, boolean value) {
        evaluationCount++;
        System.out.println("Evaluated: " + name + " (count: " + evaluationCount + ")");
        return value;
    }

    public static void main(String[] args) {
        System.out.println("=== Testing & (non-short-circuit) ===");
        evaluationCount = 0;
        boolean result1 = checkWithSideEffect("cct", false) & checkWithSideEffect("ccut", true);
        System.out.println("Result: " + result1 + " | Total evaluations: " + evaluationCount);

        System.out.println("\n=== Testing && (short-circuit) ===");
        evaluationCount = 0;
        boolean result2 = checkWithSideEffect("cct", false) && checkWithSideEffect("ccut", true);
        System.out.println("Result: " + result2 + " | Total evaluations: " + evaluationCount);
    }
}