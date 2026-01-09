package ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import model.Expense;

import static ui.ExpenseTrackerGUI.expenseTrackerApp;
import static ui.ExpenseTrackerGUI.tableModel;

/* Represents a window that allow user to filter the expenses by category or date range */
public class FilterExpenseWindow {

    JFrame filterExpenseFrame;
    JButton categoryButton;
    JButton dateRangeButton;
    JComboBox<String> categoryComboBox;
    JButton filterByCategoryButton;
    JTextField startDateField;
    JTextField endDateField;
    JButton filterByDateRangeButton;

    // EFFECTS: constructs a new window for filtering the expenses
    public FilterExpenseWindow() {
        filterFrameSetUp();

        createOptions();

        filterExpenseFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: set up frame for filtering expenses
    private void filterFrameSetUp() {
        filterExpenseFrame = new JFrame("Filter Expenses");
        filterExpenseFrame.setSize(300, 200);
        filterExpenseFrame.setLayout(new GridLayout(2, 2));
        filterExpenseFrame.setResizable(false);
        filterExpenseFrame.setLocationRelativeTo(categoryButton);
    }

    // MODIFIES: this
    // EFFECTS: create option button to filter by category or date range
    private void createOptions() {
        categoryButton = new JButton("Filter by category");
        dateRangeButton = new JButton("Filter by date range");

        filterExpenseFrame.add(categoryButton, BorderLayout.CENTER);
        filterExpenseFrame.add(dateRangeButton, BorderLayout.CENTER);

        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCategoryWindow();
            }
        });
        
        dateRangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDateRangeWindow();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: renew the frame 
    private void renewFrame() {
        filterExpenseFrame.remove(categoryButton);
        filterExpenseFrame.remove(dateRangeButton);
        filterExpenseFrame.revalidate();
        filterExpenseFrame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: open a window for filtering by category
    private void openCategoryWindow() {
        renewFrame();
        showCategoryPanel();
        filterByCategoryBtnListener();
    }

    // MODIFIES: this
    // EFFECTS: create panel for filter by category window
    private void showCategoryPanel() {
        filterExpenseFrame.setLayout(new GridLayout(3, 2));
        categoryComboBox = new JComboBox<>(new String[]{"Home", "Food", "Utilities", "Entertainment", "Other"});
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select category:"), BorderLayout.CENTER);
        filterExpenseFrame.add(panel, BorderLayout.CENTER);
        filterExpenseFrame.add(categoryComboBox, BorderLayout.CENTER);

        filterByCategoryButton = new JButton("Filter");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(filterByCategoryButton);
        filterExpenseFrame.add(buttonPanel, BorderLayout.SOUTH);
        filterExpenseFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: button listener: filter by category
    private void filterByCategoryBtnListener() {
        filterByCategoryButton.addActionListener(new ActionListener() {
            private List<Expense> expensesList;

            @Override
            public void actionPerformed(ActionEvent e) {
                String category = (String) categoryComboBox.getSelectedItem();
                tableModel.setRowCount(0);
                expensesList = expenseTrackerApp.filterExpenseByCategory(category);
                if (expensesList != null && !expensesList.isEmpty()) {
                    for (Expense expense : expensesList) {
                        Vector<String> row = new Vector<>();
                        row.add(expense.getDesc());
                        row.add(String.valueOf(expense.getAmount()));
                        row.add(expense.categoryToString());
                        row.add(String.valueOf(expense.getDate()));
                        tableModel.addRow(row);
                    }
                } 
                filterExpenseFrame.dispose();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: open a window for filtering by date range
    private void openDateRangeWindow() {
        renewFrame();
        filterExpenseFrame.setSize(450, 150);
        filterExpenseFrame.setLocationRelativeTo(null);
        filterExpenseFrame.setLayout(new GridLayout(3, 2));

        showDateRangePanel();
        filterByDateRangeBtnListener();
    }

    // MODIFIES: this
    // EFFECTS: create panel for filter by date range window
    private void showDateRangePanel() {
        startDateField = new JTextField();
        filterExpenseFrame.add(new JLabel("Start Date (YYYY-MM-DD):"));
        filterExpenseFrame.add(startDateField);

        endDateField = new JTextField();
        filterExpenseFrame.add(new JLabel("End Date (YYYY-MM-DD):"));
        filterExpenseFrame.add(endDateField);

        filterByDateRangeButton = new JButton("Filter");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(filterByDateRangeButton);
        filterExpenseFrame.add(buttonPanel, BorderLayout.SOUTH);

        filterExpenseFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: button listener: filter by date range
    private void filterByDateRangeBtnListener() {
        filterByDateRangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                tableModel.setRowCount(0);
                List<Expense> expensesList = expenseTrackerApp.filterExpenseByDateRange(startDate, endDate);
                if (expensesList != null && !expensesList.isEmpty()) {
                    for (Expense expense : expensesList) {
                        Vector<String> row = new Vector<>();
                        row.add(expense.getDesc());
                        row.add(String.valueOf(expense.getAmount()));
                        row.add(expense.categoryToString());
                        row.add(String.valueOf(expense.getDate()));
                        tableModel.addRow(row);
                    }
                } 
                filterExpenseFrame.dispose();
            }
        });
    }
}
