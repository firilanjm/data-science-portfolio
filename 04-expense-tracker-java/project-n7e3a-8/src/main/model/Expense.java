package model;

import java.time.LocalDate;

import org.json.JSONObject;
import persistence.Writable;

// Represents an expense with description name, amount (in dollars), category, and date
public class Expense implements Writable {
    private String desc;           // the description name of expense
    private double amount;         // the amount of expense
    private ExpenseType category;  // the category of expense (HOME, FOOD, UTILITIES, ENTERTAINMENT, OTHER)
    private LocalDate date;        // the date of expense

    // REQUIRES: desc has non-zero length, amount > 0.0 && date is valid date
    // EFFECTS: constructs an expense with the description name is set to desc, 
    //          expense amount is set to amount, expense category is set to category,
    //          and expense date is set to date.
    public Expense(String desc, double amount, ExpenseType category, LocalDate date) {
        this.desc = desc;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // getters
    // EFFECTS: returns the description name of this expense
    public String getDesc() {
        return desc; 
    }
    
    // EFFECTS: returns the amount of this expense
    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns the category of this expense
    public ExpenseType getCategory() {
        return category;
    }

    // EFFECTS: returns the date of this expense
    public LocalDate getDate() {
        return date;
    }

    //setters
    // MODIFIES: this
    // EFFECTS: set this desc to the given desc
    public void setDesc(String desc) {
        this.desc = desc;
    }

    // REQUIRES: amount > 0.0
    // MODIFIES: this
    // EFFECTS: set this amount to the given amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // MODIFIES: this
    // EFFECTS: set this category to the given category
    public void setCategory(ExpenseType category) {
        this.category = category;
    }

    // REQUIRES: date is valid date
    // MODIFIES: this
    // EFFECTS: set this date to the given date
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // EFFECTS: returns true if str is appropriate with this expense type
    public boolean matchesCategory(String str) {
        switch (getCategory()) {
            case HOME:
                return str.equalsIgnoreCase("Home");
            case FOOD:
                return str.equalsIgnoreCase("Food");
            case UTILITIES:
                return str.equalsIgnoreCase("Utilities");
            case ENTERTAINMENT:
                return str.equalsIgnoreCase("Entertainment");
            default:
                return str.equalsIgnoreCase("Other");
        }
    }

    // REQUIRES: the start date is before the end date
    // EFFECTS: returns true if the expense date is in the date range (inclusive)
    public boolean dateInRange(LocalDate start, LocalDate end) {
        if (getDate().equals(start) || getDate().equals(end)) {
            return true;
        } else {
            return getDate().isAfter(start) && getDate().isBefore(end);
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("desc", desc);
        json.put("amount", amount);
        json.put("category", category);
        json.put("date", date);
        return json;
    }

    public String categoryToString() {
        switch (category) {
            case HOME:
                return "Home";
            case FOOD:
                return "Food";
            case UTILITIES:
                return "Utilities";
            case ENTERTAINMENT:
                return "Entertainment";
            default:
                return "Other";
        }
    }


}