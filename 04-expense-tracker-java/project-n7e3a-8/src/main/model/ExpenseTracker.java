package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Reprensents personal expense tracker with there is no expense at the beginning
public class ExpenseTracker implements Writable {
    private List<Expense> expenses;

    // EFFECTS: constructs new expense tracker with empty expense
    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
    }

    //getters
    // EFFECTS: return the list of expenses in this expense tracker
    public List<Expense> getExpenses() {
        EventLog.getInstance().logEvent(new Event("View all expenses"));
        return expenses;
    }

    // MODIFIES: this
    // EFFECTS: add an expense to the expense tracker
    public void addExpense(Expense e) {
        expenses.add(e);
        EventLog.getInstance().logEvent(new Event("Added an expense: " + e.getDesc() + ", " + e.getAmount() + ", " 
                                                + e.categoryToString() + ", " + e.getDate()));
    }

    // MODIFIES: this
    // EFFECTS: remove an expense from the expense tracker
    public void removeExpense(Expense e) {
        expenses.remove(e);
        EventLog.getInstance().logEvent(new Event("Removed an expense: " + e.getDesc() + ", " + e.getAmount() + ", " 
                                                + e.categoryToString() + ", " + e.getDate()));
    }

    // EFFECTS: return a list of expenses only in the given category
    public List<Expense> filterByCategory(String category) {
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense e: expenses) {
            if (e.matchesCategory(category)) {
                filteredExpenses.add(e);
            }
        }
        EventLog.getInstance().logEvent(new Event("Filter the expenses by category: " + category));
        return filteredExpenses;
    }

    // REQUIRES: start and end are invalid date
    //           start date is before end date
    // EFFECTS: returns a list of expenses only in the given date range
    public List<Expense> filterByDateRange(LocalDate start, LocalDate end) {
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense e: expenses) {
            if (e.dateInRange(start, end)) {
                filteredExpenses.add(e);
            } 
        } 
        EventLog.getInstance().logEvent(new Event("Filter the expenses by date range between " 
                                                + start + " and " + end));
        return filteredExpenses;
    }

    // EFFECTS: returns the total amount of expenses in the expense tracker
    public double getTotalExpense() {
        double amount = 0.0;
        for (Expense e: expenses) {
            amount += e.getAmount();
        }
        return amount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expenses", expensesToJson());
        return json;
    }

    // EFFECTS: returns expenses in this expense tracker as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Expense e: expenses) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
