package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Vector;

import model.Expense;
import model.ExpenseType;

import static model.ExpenseType.*;

import static ui.ExpenseTrackerGUI.expenseTrackerApp;
import static ui.ExpenseTrackerGUI.tableModel;

/* Represents a window that allow user to add a new expense */
public class AddExpenseWindow {

    JFrame addExpenseFrame;
    JTextField nameField;
    JTextField amountField;
    JComboBox<String> categoryComboBox;
    JTextField dateField;
    JButton addButton;
    Dimension frameDimensions;
    Expense expense;

    // EFFECTS: constructs a new window for add new expense
    public AddExpenseWindow() {
        addFrameSetUp();
        createTextFields();
        addExpenseFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up frame for adding new expense
    private void addFrameSetUp() {
        addExpenseFrame = new JFrame("Add New Expense");
        addExpenseFrame.setSize(450, 300);
        addExpenseFrame.setLayout(new GridLayout(5, 2));
        addExpenseFrame.setResizable(false);
        addExpenseFrame.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: create a text field of expense name, amount, category, date
    //          to be filled by the user
    private void createTextFields() {
        nameField = new JTextField();
        addExpenseFrame.add(new JLabel("Name:"));
        addExpenseFrame.add(nameField);

        amountField = new JTextField();
        addExpenseFrame.add(new JLabel("Amount:"));
        addExpenseFrame.add(amountField);

        categoryComboBox = new JComboBox<>(new String[]{"Home", "Food", "Utilities", "Entertainment", "Other"});
        addExpenseFrame.add(new JLabel("Category:"));
        addExpenseFrame.add(categoryComboBox);

        dateField = new JTextField();
        addExpenseFrame.add(new JLabel("Date (YYYY-MM-DD):"));
        addExpenseFrame.add(dateField);

        addButton = new JButton("Add expense");
        addButton.setSize(10, 5);
        addExpenseFrame.add(addButton);
        addButtonActionListener();
    }

    // MODIFIES: this
    // EFFECTS: button listener: add expense
    private void addButtonActionListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String amountText = amountField.getText();
                String category = (String) categoryComboBox.getSelectedItem();
                String date = dateField.getText();

                double amount = Double.parseDouble(amountText);
                expense = new Expense(name, amount, toExpenseType(category), LocalDate.parse(date));
                expenseTrackerApp.getExpenseTracker().addExpense(expense);

                addExpenseToTable(name, amount, category, date);

                // Close the add expense window
                addExpenseFrame.dispose();
            }
        });
    }

    // MODIFIES: tableModel
    // EFFECTS: add an expense with given name, amount, category, and date
    //          to the tableModel
    private void addExpenseToTable(String name, double amount, String category, String date) {
        Vector<String> row = new Vector<>();
        row.add(name);
        row.add(String.valueOf(amount));
        row.add(category);
        row.add(String.valueOf(date));
        tableModel.addRow(row);

        // Clear input fields
        nameField.setText("");
        amountField.setText("");
        dateField.setText("");
    }

    // MODIFIES: this
    // EFFECTS: change the given category to the appropriate ExpenseType
    private ExpenseType toExpenseType(String category) {
        switch (category) {
            case "Home":
                return HOME;
            case "Food":
                return FOOD;
            case "Utilities":
                return UTILITIES;
            case "Entertainment":
                return ENTERTAINMENT;
            default:
                return OTHER;
        }
    }

}
