package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import model.Expense;
import model.ExpenseType;

public class JsonTest {
    protected void checkExpense(String desc, double amount, ExpenseType category, LocalDate date, Expense expense) {
        assertEquals(desc, expense.getDesc());
        assertEquals(amount, expense.getAmount());
        assertEquals(category, expense.getCategory());
        assertEquals(date, expense.getDate());
    }
}
