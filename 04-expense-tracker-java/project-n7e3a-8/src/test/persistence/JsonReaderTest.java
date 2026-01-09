package persistence;

import model.ExpenseType;
import model.Expense;
import model.ExpenseTracker;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {
    private JsonReader reader;
    private ExpenseTracker expenseTracker;
    
    @Test
    void testReaderNonExistentFile() {
        reader = new JsonReader("./data/noSuchFile.json");
        try {
            expenseTracker = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyExpenseTracker() {
        reader = new JsonReader("./data/testReaderEmptyExpenseTracker.json");
        try {
            expenseTracker = reader.read();
            assertTrue(expenseTracker.getExpenses().isEmpty());
            assertEquals(0, expenseTracker.getExpenses().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralExpenseTracker() {
        reader = new JsonReader("./data/testReaderGeneralExpenseTracker.json");
        try {
            expenseTracker = reader.read();
            List<Expense> expenses = expenseTracker.getExpenses();
            assertEquals(2, expenses.size());
            checkExpense("grocery", 200, ExpenseType.FOOD, LocalDate.parse("2024-10-31"), expenses.get(0));
            checkExpense("rent", 700, ExpenseType.HOME, LocalDate.parse("2024-12-10"), expenses.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
