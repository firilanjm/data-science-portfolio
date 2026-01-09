package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static model.ExpenseType.*;

class ExpenseTest {
    private Expense testExpense1;
    private Expense testExpense2;
    private Expense testExpense3;
    private Expense testExpense4;
    private Expense testExpense5;

    @BeforeEach
    void runBefore() {
        testExpense1 = new Expense("Groceries", 250.0, FOOD, LocalDate.parse("2024-10-07"));
        testExpense2 = new Expense("Rent", 915.3, HOME, LocalDate.parse("2024-12-19"));
        testExpense3 = new Expense("Electricity", 55.7, UTILITIES, LocalDate.parse("2024-10-21"));
        testExpense4 = new Expense("Concert", 159.99, ENTERTAINMENT, LocalDate.parse("2024-11-30"));
        testExpense5 = new Expense("Bike", 300.0, OTHER, LocalDate.parse("2024-08-10"));

    }

    @Test
    void testConstructor() {
        assertEquals("Groceries", testExpense1.getDesc());
        assertEquals(250.0, testExpense1.getAmount());
        assertEquals(FOOD, testExpense1.getCategory());
        assertEquals(LocalDate.parse("2024-10-07"), testExpense1.getDate());
    }

    @Test
    void testSetters() {
        testExpense1.setDesc("Housing");
        testExpense1.setAmount(1000.0);
        testExpense1.setCategory(HOME);
        testExpense1.setDate(LocalDate.parse("2024-10-07"));

        assertEquals("Housing", testExpense1.getDesc());
        assertEquals(1000.0, testExpense1.getAmount());
        assertEquals(HOME, testExpense1.getCategory());
        assertEquals(LocalDate.parse("2024-10-07"), testExpense1.getDate());
    }

    @Test
    void testTypeMatchesCategory() {
        assertTrue(testExpense1.matchesCategory("food"));
        assertFalse(testExpense1.matchesCategory("home"));

        assertTrue(testExpense2.matchesCategory("home"));
        assertFalse(testExpense2.matchesCategory("utilities"));

        assertTrue(testExpense3.matchesCategory("utilities"));
        assertFalse(testExpense3.matchesCategory("entertainment"));

        assertTrue(testExpense4.matchesCategory("entertainment"));
        assertFalse(testExpense4.matchesCategory("other"));

        assertTrue(testExpense5.matchesCategory("other"));
        assertFalse(testExpense5.matchesCategory("food"));
    }

    @Test
    void testDateInRange() {
        assertTrue(testExpense1.dateInRange(LocalDate.parse("2024-10-04"), LocalDate.parse("2024-10-08")));
        assertFalse(testExpense1.dateInRange(LocalDate.parse("2024-10-01"), LocalDate.parse("2024-10-06")));
        assertFalse(testExpense1.dateInRange(LocalDate.parse("2024-10-10"), LocalDate.parse("2024-10-31")));
        assertFalse(testExpense1.dateInRange(LocalDate.parse("2024-12-10"), LocalDate.parse("2024-12-31")));
    }

    @Test
    void testDateInRangeBoundary() {
        assertTrue(testExpense1.dateInRange(LocalDate.parse("2024-10-07"), LocalDate.parse("2024-10-31")));
        assertTrue(testExpense1.dateInRange(LocalDate.parse("2024-10-01"), LocalDate.parse("2024-10-07")));
        assertTrue(testExpense1.dateInRange(LocalDate.parse("2024-10-07"), LocalDate.parse("2024-10-07")));
    }

    @Test
    void testCategoryToString() {
        assertEquals("Food", testExpense1.categoryToString());
        assertEquals("Home", testExpense2.categoryToString());
        assertEquals("Utilities", testExpense3.categoryToString());
        assertEquals("Entertainment", testExpense4.categoryToString());
        assertEquals("Other", testExpense5.categoryToString());
    }
    
}
