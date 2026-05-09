package org.unitime.commons.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.unitime.commons.MultiComparable;

public class MultiComparableTest {

    // test 1: same content should be equal
    @Test
    void testSameContent_shouldBeEqual() {
        MultiComparable a = new MultiComparable("John", "Smith");
        MultiComparable b = new MultiComparable("John", "Smith");

        // with equals() override it will be true without false
        assertTrue(a.equals(b), "Two objects with same content must be equal");
    }

    // test 2: different content should not be equal
    @Test
    void testDifferentContent_shouldNotBeEqual() {
        MultiComparable a = new MultiComparable("John", "Smith");
        MultiComparable b = new MultiComparable("Jane", "Doe");

        assertFalse(a.equals(b), "Different content must not be equal");
    }

    // test 3: compareTo says 0, equals must say true
    @Test
    void testCompareToZero_equalsMustBeTrue() {
        MultiComparable a = new MultiComparable("Math", 101);
        MultiComparable b = new MultiComparable("Math", 101);

        assertEquals(0, a.compareTo(b), "compareTo says they are equal");
        assertTrue(a.equals(b), "equals must agree with compareTo");
    }

    // test 4: null safety
    @Test
    void testEqualsWithNull_returnsFalse() {
        MultiComparable a = new MultiComparable("Any", "Value");

        assertFalse(a.equals(null), "equals(null) must return false, not crash");
    }

    // test 5: different type returns false
    @Test
    void testEqualsWithWrongType_returnsFalse() {
        MultiComparable a = new MultiComparable("Test", 123);
        String notMultiComparable = "Just a string";

        assertFalse(a.equals(notMultiComparable), "Different type must return false");
    }
}