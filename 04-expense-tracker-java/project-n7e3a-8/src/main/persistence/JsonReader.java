package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.json.JSONArray;

import model.Event;
import model.EventLog;
import model.Expense;
import model.ExpenseTracker;
import model.ExpenseType;

// Represents a reader that reads expense tracker from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads expense tracker from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ExpenseTracker read() throws IOException {
        EventLog.getInstance().logEvent(new Event("Loaded your expenses from file"));
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExpenseTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses expense tracker from JSON object and returns it
    private ExpenseTracker parseExpenseTracker(JSONObject jsonObject) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        addExpenses(expenseTracker, jsonObject);
        return expenseTracker;
    }

    // MODIFIES: expenseTracker
    // EFFECTS: parses expenses list from JSON object and adds them to expense tracker
    private void addExpenses(ExpenseTracker expenseTracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(expenseTracker, nextExpense);
        }
    }

    // MODIFIES: expenseTracker
    // EFFECTS: parses expense from JSON object and adds it to expense tracker
    private void addExpense(ExpenseTracker expenseTracker, JSONObject jsonObject) {
        String desc = jsonObject.getString("desc");
        double amount = jsonObject.getDouble("amount");
        ExpenseType category = ExpenseType.valueOf(jsonObject.getString("category"));
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        Expense expense = new Expense(desc, amount, category, date);
        expenseTracker.addExpense(expense);
    }
}
