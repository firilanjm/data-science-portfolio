package persistence;

import model.ExpenseType;
import model.Expense;
import model.ExpenseTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    private ExpenseTracker expenseTracker;

    @Test
    void testWriterInvalidFile() {
        try {
            expenseTracker  = new ExpenseTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyExpenseTracker() {
        try {
            expenseTracker = new ExpenseTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyExpenseTracker.json");
            writer.open();
            writer.write(expenseTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyExpenseTracker.json");
            expenseTracker = reader.read();
            assertTrue(expenseTracker.getExpenses().isEmpty());
            assertEquals(0, expenseTracker.getExpenses().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralExpenseTracker() {
        try {
            expenseTracker = new ExpenseTracker();
            expenseTracker.addExpense(new Expense("rent", 800, ExpenseType.HOME, LocalDate.parse("2024-11-12")));
            expenseTracker.addExpense(new Expense("coffee", 9.5, ExpenseType.OTHER, LocalDate.parse("2024-01-15")));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralExpenseTracker.json");
            writer.open();
            writer.write(expenseTracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralExpenseTracker.json");
            expenseTracker = reader.read();
            List<Expense> expenses = expenseTracker.getExpenses();
            assertEquals(2, expenses.size());
            checkExpense("rent", 800, ExpenseType.HOME, LocalDate.parse("2024-11-12"), expenses.get(0));
            checkExpense("coffee", 9.5, ExpenseType.OTHER, LocalDate.parse("2024-01-15"), expenses.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
