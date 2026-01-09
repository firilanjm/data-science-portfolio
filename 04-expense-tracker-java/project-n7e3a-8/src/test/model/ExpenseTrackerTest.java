package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static model.ExpenseType.*;


class ExpenseTrackerTest {
    private ExpenseTracker testExpenseTracker;
    private List<Expense> testExpenses;
    private List<Expense> testFilteredExpenses;
    private Expense e0;
    private Expense e1;
    private Expense e2;
    private Expense e3;

    @BeforeEach
    void runBefore() {
        testExpenseTracker = new ExpenseTracker();
        testExpenses = testExpenseTracker.getExpenses();
        e1 = new Expense("Grocery", 300, FOOD, LocalDate.parse("2024-10-15"));
        e2 = new Expense("Rent", 1000, HOME, LocalDate.parse("2024-09-07"));
        e3 = new Expense("Pho Soup", 21.2, FOOD, LocalDate.parse("2024-09-21"));
    }

    @Test
    void testAddExpenseSingleExpense() {
        testExpenseTracker.addExpense(e1);
        assertFalse(testExpenses.isEmpty());
        assertEquals(1, testExpenses.size());

        e0 = testExpenses.get(0);
        assertEquals("Grocery", e0.getDesc());
        assertEquals(300, e0.getAmount());
        assertEquals(FOOD, e0.getCategory());
        assertEquals(LocalDate.parse("2024-10-15"), e0.getDate());
    }

    @Test
    void testAddExpenseMultipleExpense() {
        testExpenseTracker.addExpense(e1);
        testExpenseTracker.addExpense(e2);
        assertFalse(testExpenses.isEmpty());
        assertEquals(2, testExpenses.size());
        assertEquals(e1, testExpenses.get(0));
        assertEquals(e2, testExpenses.get(1));

        e0 = testExpenses.get(0);
        assertEquals("Grocery", e0.getDesc());
        assertEquals(300, e0.getAmount());
        assertEquals(FOOD, e0.getCategory());
        assertEquals(LocalDate.parse("2024-10-15"), e0.getDate());
    }

    @Test
    void testRemoveExpense() {
        testExpenseTracker.addExpense(e1);
        assertFalse(testExpenses.isEmpty());
        assertEquals(1, testExpenses.size());

        testExpenseTracker.removeExpense(e1);
        assertTrue(testExpenses.isEmpty());
        assertEquals(0, testExpenses.size());
    }

    @Test 
    void testFilterByCategory() {
        testExpenseTracker.addExpense(e1);
        testExpenseTracker.addExpense(e2);
        testExpenseTracker.addExpense(e3);
        testFilteredExpenses = testExpenseTracker.filterByCategory("food");
        e0 = testFilteredExpenses.get(0);
        e1 = testFilteredExpenses.get(1);

        assertEquals(2, testFilteredExpenses.size());
        assertEquals(FOOD, e0.getCategory());
        assertEquals(FOOD, e1.getCategory());
    }

    @Test 
    void testFilterByDateRangeInRange() {
        testExpenseTracker.addExpense(e1);
        testExpenseTracker.addExpense(e2);
        testExpenseTracker.addExpense(e3);
        testFilteredExpenses = testExpenseTracker.filterByDateRange(LocalDate.parse("2024-09-01"),
                               LocalDate.parse("2024-09-30"));
        e0 = testFilteredExpenses.get(0);
        e1 = testFilteredExpenses.get(1);

        assertEquals(2, testFilteredExpenses.size());
        assertEquals(LocalDate.parse("2024-09-07"), e0.getDate());
        assertEquals(LocalDate.parse("2024-09-21"), e1.getDate());
    }

    @Test
    void testFilterByDateRangeNotInRange() {
        e1 = new Expense("Grocery", 300, FOOD, LocalDate.parse("2024-10-15"));
        e2 = new Expense("Rent", 1000, HOME, LocalDate.parse("2024-09-07"));
        e3 = new Expense("Pho Soup", 21.2, FOOD, LocalDate.parse("2024-09-21"));

        testExpenseTracker.addExpense(e1);
        testExpenseTracker.addExpense(e2);
        testExpenseTracker.addExpense(e3);
        testFilteredExpenses = testExpenseTracker.filterByDateRange(LocalDate.parse("2024-09-01"),
                               LocalDate.parse("2024-09-06"));

        assertEquals(0, testFilteredExpenses.size());
    }

    @Test
    void testGetTotalExpenseNoExpense() {
        assertEquals(0, testExpenseTracker.getTotalExpense());
    }

    @Test
    void testGetTotalExpenseSingleExpense() {
        testExpenseTracker.addExpense(e1);
        assertEquals(300, testExpenseTracker.getTotalExpense());
    }

    @Test
    void testGetTotalExpenseMultipleExpense() {
        testExpenseTracker.addExpense(e1);
        testExpenseTracker.addExpense(e2);
        testExpenseTracker.addExpense(e3);
        assertEquals(1321.2, testExpenseTracker.getTotalExpense());
    }

}
