package ui;

import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import model.Expense;
import model.ExpenseTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import static model.ExpenseType.*;

// This class deals with keep the program running, and it represents the underlying
// operations for the graphical user interface
public class ExpenseTrackerApp {
    private static final String JSON_STORE = "./data/expenseTracker.json";
    private Expense expense;
    private static ExpenseTracker expenseTracker;
    private List<Expense> expensesList;
    private Scanner input;
    private boolean runProgram;
    private static JsonWriter jsonWriter;
    private static JsonReader jsonReader;
    
    // EFFECTS: initializes the expense tracker, constructs json writer and json reader, 
    //          and runs the personal expense tracker application
    public ExpenseTrackerApp() {
        expenseTracker = new ExpenseTracker();
        expensesList = expenseTracker.getExpenses();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        //runExpenseTracker();
    }

    public ExpenseTracker getExpenseTracker() {
        return expenseTracker;
    }

    public List<Expense> getExpensesList() {
        return expensesList;
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runExpenseTracker() {
        input = new Scanner(System.in);
        runProgram = true;
        String command;
        System.out.println("Welcome to Your Personal Expense Tracker App!");
 
        while (runProgram) {
            displayMenu();
            command = getUserInputString();
            if (command.equals("q")) {
                System.out.println("Thank you for using Personal Expense Tracker");
                System.out.println("Have a nice day! ");
                runProgram = false;
            } else {
                parseInput(command);
            }
        }
    }

    // EFFECTS: display menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select the following request:");
        System.out.println("Enter 'a' to add an expense");
        System.out.println("Enter 'm' to modify an existing expense");
        System.out.println("Enter 'r' to remove an exisiting expense");
        System.out.println("Enter 'v' to view list of all expenses");
        System.out.println("Enter 'f' to view list of all expenses filtered by category or date range");
        System.out.println("Enter 's' to save your expense tracker to file");
        System.out.println("Enter 'l' to load your expense tracker from file");
        System.out.println("Enter 'q' to quit the application");
    }

    // MODIFIES: this
    // EFFECTS: prints menu options and info depending on input command
    private void parseInput(String command) {
        if (command.equals("a")) {
            addingExpense();
        } else if (command.equals("m")) {
            modifyingExpense();
        } else if (command.equals("r")) {
            removingExpense();
        } else if (command.equals("v")) {
            viewingExpenses();
        } else if (command.equals("f")) {
            filteringExpenses();
        } else if (command.equals("s")) {
            saveExpenseTracker();
        } else if (command.equals("l")) {
            loadExpenseTracker();
        } else {
            System.out.println("Selection is invalid.");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a new expense into expense tracker
    private void addingExpense() {
       // Scanner input = new Scanner(System.in);
        expense = new Expense("", 0, null, null);
        System.out.println("Enter the name of expense:    (e.g. 'grocery')");
        String name = input.nextLine();
        expense.setDesc(name);

        System.out.println("Enter the amount of expense: ");
        Double amount = input.nextDouble();
        expense.setAmount(amount);

        input.nextLine();

        System.out.println("Please select the category of expense:  (strictly all must be in lower case)");
        System.out.println("'home', 'food', 'utilities', 'entertainment', 'other'");
        String str = input.nextLine();
        commandToExpenseType(str, expense);

        System.out.println("Enter the date of expense:  (e.g. '2024-10-31')");
        String date = input.nextLine();
        expense.setDate(LocalDate.parse(date));

        expenseTracker.addExpense(expense);
        confirmingAddedExpense(expense);
    }

    // MODIFIES: this
    // EFFECTS: set the category expense with the appropriate command by user
    private void commandToExpenseType(String command, Expense expense) {
        switch (command) {
            case "home":
                expense.setCategory(HOME);
                break;
            case "food":
                expense.setCategory(FOOD);
                break;
            case "utilities":
                expense.setCategory(UTILITIES);
                break;
            case "entertainment":
                expense.setCategory(ENTERTAINMENT);
                break;
            default:
                expense.setCategory(OTHER);
                break;
        }
    }

    // EFFECTS: prints confirmation that the given expense has been added
    private void confirmingAddedExpense(Expense e) {
        System.out.println("The expense with detail:");
        System.out.println("Desc: " + e.getDesc());
        System.out.println("Amount: " + e.getAmount());
        System.out.println("Category: " + e.getCategory());
        System.out.println("Date: " + e.getDate());
        System.out.println("has been succesfully added into your expense tracker");
    }

    // MODIFIES: this
    // EFFECTS: modify an existing expense depending on user input
    private void modifyingExpense() {
        expensesList = expenseTracker.getExpenses();
        if (expensesList.isEmpty()) {
            System.out.println("Sorry, there is no expense to be modified");
        } else {
            selectExpense();
        }
    }

    // MODIFIES: this
    // EFFECTS: select the field of expense to be modified depending on user input
    private void selectExpense() {
        System.out.println("Please select the expense to be modified.");
        System.out.println("(Enter '1' to modify first expense in the list below)");
        System.out.println("no. description, amount, category, date");
        int count = 1;
        for (Expense e: expensesList) {
            System.out.println(String.valueOf(count) + ". " + e.getDesc() + ", " + e.getAmount() + ", "
                               + e.getCategory() + ", " + e.getDate());
            count++;
        }
        int num = input.nextInt();
        Expense modifiedExpense = expensesList.get(num - 1);
        input.nextLine();
        whichDetail();
        String userInput = input.nextLine();
        modifyAnExpense(userInput, modifiedExpense);  
    }

    // EFFECTS: asks the user which detail they want to modify
    private void whichDetail() {
        System.out.println("Which detail of the expense you want to modify?   ");
        System.out.println("Please select from the following options");
        System.out.println("'desc' -> the expense description name");
        System.out.println("'amount' ->the expense amount");
        System.out.println("'category' -> the expense category");
        System.out.println("'date' -> the expense date");
        System.out.println("strictly all must be in lower case");
    }

    // MODIFIES: expense
    // EFFECTS: print different instructions and modify an expense field depending on user input
    private void modifyAnExpense(String command, Expense expense) {
        if (command.equals("desc")) {
            modifyDesc(expense);
        } else if (command.equals("amount")) {
            modifyAmount(expense);
        } else if (command.equals("category")) {
            modifyCategory(expense);
        } else if (command.equals("date")) {
            modifyDate(expense);
        } else {
            System.out.println("Selection is invalid");
        }
    }

    // MODIFIES: expense
    // EFFECTS: modify the description name of the given expense
    private void modifyDesc(Expense expense) {
        System.out.println("Please enter the description name:  (e.g. 'rent')");
        String desc = input.nextLine();
        expense.setDesc(desc);
        confirmingModifiedExpense(expense);
    }

    // MODIFIES: expense
    // EFFECTS: modify the amount of the given expense
    private void modifyAmount(Expense expense) {
        System.out.println("Please enter the amount: ");
        String userInput = input.nextLine();
        double amount = Double.parseDouble(userInput);
        expense.setAmount(amount);
        confirmingModifiedExpense(expense);
    }

    // MODIFIES: expense
    // EFFECTS: modify the category of the given expense
    private void modifyCategory(Expense expense) {
        System.out.println("Please select the category: ");
        System.out.println("'home', 'food', 'utilities', 'entertainment, or 'other'");
        String category = input.nextLine();
        if (category.equals("home") || category.equals("food") || category.equals("utilities")
                   || category.equals("entertainment") || category.equals("other")) {
            commandToExpenseType(category, expense);
            confirmingModifiedExpense(expense);
        } else {
            System.out.println("Selection is invalid.");
        }
    }

    // MODIFIES: expense
    // EFFECTS: modify the date of the given expense
    private void modifyDate(Expense expense) {
        System.out.println("Please enter the date:");
        String command2 = input.nextLine();
        expense.setDate(LocalDate.parse(command2));
        confirmingModifiedExpense(expense);
    }

    // EFFECTS: prints the confirmation that the expense has been changed
    private void confirmingModifiedExpense(Expense modifiedExpense) {
        System.out.println("The description name of expense has been changed.");
        System.out.println("The modified expense with detail:");
        System.out.println("Desc: " + modifiedExpense.getDesc());
        System.out.println("Amount: " + modifiedExpense.getAmount());
        System.out.println("Category: " + modifiedExpense.getCategory());
        System.out.println("Date: " + modifiedExpense.getDate());
    }

    // MODIFIES: this
    // EFFECTS: remove selected expense by user from the expense tracker
    private void removingExpense() {
        expensesList = expenseTracker.getExpenses();
        if (expensesList.isEmpty()) {
            System.out.println("Sorry, there is no expense in your personal expense tracker.");
        } else { 
            System.out.println("Please select the expense to be removed");
            System.out.println("(e.g. enter '1' to remove first expense in the list below)");
            System.out.println("no. description, amount, category, date");
            int count = 1;
            for (Expense e: expensesList) {
                System.out.println(String.valueOf(count) + ". " + e.getDesc() + ", " + e.getAmount() + ", "
                                   + e.getCategory() + ", " + e.getDate());
                count++;
            }
            int num = input.nextInt();
            expensesList.remove(num - 1);
            confirmingRemovedExpense(expensesList);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the confirmation that the expense has been removed
    //          and returns the remaining expenses in the expense tracker
    private void confirmingRemovedExpense(List<Expense> expensesList) {
        System.out.println("The selected expense has been removed from your expense tracker");
        System.out.println("Below is your current expense tracker:");
        int count = 1;
        for (Expense e: expensesList) {
            System.out.println(String.valueOf(count) + " " + e.getDesc() + ", " + e.getAmount() + ", "
                               + e.getCategory() + ", " + e.getDate());
            count++;
        }
    }

    // EFFECTS: prints all of expenses in the expense tracker
    void viewingExpenses() {
        expensesList = expenseTracker.getExpenses();
        if (expensesList.isEmpty()) {
            System.out.println("Sorry, there is no expense in your personal expense tracker.");
        } else {
            System.out.println("Below is your personal expense tracker");
            System.out.println("no. description, amount, category, date");
            int count = 1;
            for (Expense e: expensesList) {
                System.out.println(String.valueOf(count) + ". " + e.getDesc() + ", " + e.getAmount() + ", "
                                   + e.getCategory() + ", " + e.getDate());
                count++;
            }
            System.out.println("Your total expense is: " + expenseTracker.getTotalExpense());
        }
    }

    // EFFECTS: prints of all expenses in the expense tracker

    // EFFECTS: prints all of expenses in the given category or date range depending on user input
    private void filteringExpenses() {
        expensesList = expenseTracker.getExpenses();
        if (expensesList.isEmpty()) {
            System.out.println("Sorry, there is no expense in your personal expense tracker.");
        } else {
            System.out.println("Please select the following: ");
            System.out.println("Enter 'c' for filtering by category");
            System.out.println("Enter 'd' for filtering by date range");

            String command = input.nextLine();
            if (command.equals("c")) {
                filteringByCategory();
            } else if (command.equals("d")) {
                filteringByDateRange();
            } else {
                System.out.println("Selection is invalid.");
            }
        } 
    }

    // EFFECTS: filter the expenses only in the given category input by user
    protected void filteringByCategory() {
        System.out.println("Please select the category you want: ");
        System.out.println("Enter 'home', 'food', 'utilities', 'entertainment', or 'other'");
        System.out.println("strictly all must be in lower case");
        String category = input.nextLine();

        System.out.println("Below is the list of expenses in " + category + " category");
        System.out.println("no. description, amount, category, date");
        int count = 1;
        double getTotalAmountFiltered = 0;
        for (Expense e: filterExpenseByCategory(category)) {
            System.out.println(String.valueOf(count) + ". " + e.getDesc() + ", " 
                              + e.getAmount() + ", " + e.getCategory()  + ", " + e.getDate());
            getTotalAmountFiltered += e.getAmount();
            count++;
        }
        System.out.println("Your total expenses in " + category + " category is: " + getTotalAmountFiltered);
    }

    // EFFECTS: filter the expenses only in the given date range input by user
    private void filteringByDateRange() {
        System.out.println("Enter start date:  (eg. must be in form '2024-10-31' )");
        String start = input.nextLine();
        System.out.println("Enter end date:    (eg. must be in form '2024-10-31' )");
        String end = input.nextLine();

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        if (startDate.isBefore(endDate)) { 
            System.out.println("Below is the list of expenses from " + start + " to " + end);
            System.out.println("no. description, amount, category, date");
            int count = 1;
            double getTotalAmountFiltered = 0;
            for (Expense e: filterExpenseByDateRange(start, end)) {
                System.out.println(String.valueOf(count) + ". " + e.getDesc() + ", "
                               + e.getAmount() + ", " + e.getCategory() + ", " + e.getDate());
                getTotalAmountFiltered += e.getAmount();
                count++;
            }
            System.out.println("Your total expenses from " + start + " to " + end + " is: " + getTotalAmountFiltered);
        } else {
            System.out.println("Your date range selection is invalid.");
        }
    }

    // EFFECTS: prints all of expenses in the given category
    protected List<Expense> filterExpenseByCategory(String category) {
        return expenseTracker.filterByCategory(category);
    }

    // REQUIRES: start date is before en date
    // EFFECTS: prints all of expenses in the given date range
    protected List<Expense> filterExpenseByDateRange(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return expenseTracker.filterByDateRange(startDate, endDate);
    }

    // EFFECTS: saves the expense tracker to file
    public void saveExpenseTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(expenseTracker);
            jsonWriter.close();
            System.out.println("Saved your expense tracker to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads expense tracker from file
    public void loadExpenseTracker() {
        try {
            expenseTracker = jsonReader.read();
            System.out.println("Loaded your expense tracker from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s) {
        s = s.toLowerCase();
        return s;
    }

    // EFFECTS: get the user input string
    private String getUserInputString() {
        String str = "";
        if (input.hasNext()) {
            str = input.nextLine();
            str = makePrettyString(str);
        }
        return str;
    }
}
