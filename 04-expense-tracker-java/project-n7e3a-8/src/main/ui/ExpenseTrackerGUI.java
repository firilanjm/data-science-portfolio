package ui;

import java.util.List;
import java.util.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

import model.Event;
import model.EventLog;
import model.Expense;

/* Represents Personal Expense Tracker Application in Graphical User Interface form
 * when user can add new expense, remove existing expense, filter expenses by category 
 * or date range, view all of expenses, save expense(s) to data and load expense(s) from data
 */
public class ExpenseTrackerGUI extends JFrame {

    JTable expenseTable;
    JScrollPane scrollPane;

    static ExpenseTrackerApp expenseTrackerApp;
    static DefaultTableModel tableModel;
    
    Image scaledImage;
    Icon newIcon;

    JPanel buttonPanel;
    JButton openAddExpenseButton;
    JButton openFilterExpenseButton;
    JButton removeExpenseButton;
    JButton filterExpenseButton;
    JButton viewAllExpenseButton;

    public static void main(String[] args) {
        new ExpenseTrackerGUI();
    }

    // EFFECTS: constructs a new window
    public ExpenseTrackerGUI() {
        expenseTrackerApp = new ExpenseTrackerApp();
        frameSetUp();

        showButtonPanels();

        showTables();
        
        setVisible(true);
        expensesLoadingPopUpMsg();
    }

    // MODIFIES: this
    // EFFECTS: sets up the frame
    private void frameSetUp() {
        setTitle(" Personal Expense Tracker");
        setSize(750, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setOnClosePopUpMsg();
    }

    // MODIFIES: this
    // EFFECTS: listener for the exit program button
    private void setOnClosePopUpMsg() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeApplication();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: display the confirmation dialog of quitting the program
    private void closeApplication() {
        String[] responses = {"Save and Quit", "Quit without saving"};
        ImageIcon saveIcon = new ImageIcon("src/res/save_data.png");

        int confirmed = JOptionPane.showOptionDialog(getContentPane(),
                "You have unsaved data",
                "Data aren't saved!", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, saveIcon, responses, responses[0]);

        if (confirmed == 0) {
            expenseTrackerApp.saveExpenseTracker();
            printLog(EventLog.getInstance());
            System.exit(0);
        } else if (confirmed == 1) {
            printLog(EventLog.getInstance());
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: display the starter dialog of loading up the data
    private void expensesLoadingPopUpMsg() {
        String[] responses = {"Load my expense list", "Create new expense list"};

        ImageIcon loadIcon = new ImageIcon("src/res/load.png");

        int confirmed = JOptionPane.showOptionDialog(getContentPane(),
                "Do you want to load your expense tracker from file?",
                "Welcome to Personal Expense Tracker!", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, loadIcon, responses, responses[0]);

        if (confirmed == 0) {
            expenseTrackerApp.loadExpenseTracker();
            expenseTrackerApp.viewingExpenses();
            List<Expense> expensesList = expenseTrackerApp.getExpensesList();
            if (expensesList != null && !expensesList.isEmpty()) {
                displayExpenses(expensesList);
            } else {
                JOptionPane.showMessageDialog(getContentPane(), "No expenses loaded.", 
                        "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: Show panel for buttons
    private void showButtonPanels() {
        ImageIcon addIcon = new ImageIcon("src/res/add_item.png");
        openAddExpenseButton = new JButton("Add expense");
        showButtonWithIcon(addIcon, openAddExpenseButton);

        ImageIcon removeIcon = new ImageIcon("src/res/remove.png");
        removeExpenseButton = new JButton("Remove expense");
        showButtonWithIcon(removeIcon, removeExpenseButton);

        ImageIcon filterIcon = new ImageIcon("src/res/filter.png");
        openFilterExpenseButton = new JButton("Filter expense");
        showButtonWithIcon(filterIcon, openFilterExpenseButton);

        ImageIcon viewAllIcon = new ImageIcon("src/res/view_all.png");
        viewAllExpenseButton = new JButton("View all expense");
        showButtonWithIcon(viewAllIcon, viewAllExpenseButton);

        addButtonToPanel();
        openBtnListener();
    }

    // MODIFIES: this
    // EFFECTS: Show panel for buttons
    private void showButtonWithIcon(ImageIcon icon, JButton button) {
        scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); 
        newIcon = new ImageIcon(scaledImage);
        button.setIcon(newIcon);
    }

    // MODIFIES: this
    // EFFECTS: add buttons to panel
    private void addButtonToPanel() {
        buttonPanel = new JPanel();
        buttonPanel.add(openAddExpenseButton);
        buttonPanel.add(removeExpenseButton);
        buttonPanel.add(openFilterExpenseButton);
        buttonPanel.add(viewAllExpenseButton);
        add(buttonPanel, BorderLayout.NORTH);
    }


    // MODIFIES: this
    // EFFECTS: button listener: open task with appropriate button
    private void openBtnListener() {
        addExpenseBtnListener();
        removeExpenseBtnListener();
        filterExpenseBtnListener();
        viewAllExpenseBtnListener();
    }

    // MODIFIES: this
    // EFFECTS: button listener: open add expense window
    private void addExpenseBtnListener() {
        openAddExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddExpenseWindow();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: button listener: remove selected expense(s) 
    private void removeExpenseBtnListener() {
        removeExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = expenseTable.getSelectedRow();
                if (selectedRow != -1) {
                    Expense selectedExpense = expenseTrackerApp.getExpensesList().get(selectedRow);
                    expenseTrackerApp.getExpenseTracker().removeExpense(selectedExpense);
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(getContentPane(), "Please select an expense to remove",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: button listener: open filter expense window
    private void filterExpenseBtnListener() {
        openFilterExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FilterExpenseWindow();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: button listener: display all of expenses
    private void viewAllExpenseBtnListener() {
        viewAllExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setNumRows(0);
                List<Expense> expensesList = expenseTrackerApp.getExpenseTracker().getExpenses();
                if (expensesList != null && !expensesList.isEmpty()) {
                    displayExpenses(expensesList);
                } else {
                    JOptionPane.showMessageDialog(getContentPane(), "There is no expenses in your expense tracker",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: display the table of expenses list have been added
    private void showTables() {
        String[] columnNames = {"Name", "Amount", "Category", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        expenseTable = new JTable(tableModel);
        scrollPane = new JScrollPane(expenseTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: display the given list of expenses
    private void displayExpenses(List<Expense> expensesList) {
        for (Expense e : expensesList) {
            Vector<String> row = new Vector<>();
            row.add(e.getDesc());
            row.add(String.valueOf(e.getAmount()));
            row.add(e.categoryToString());
            row.add(String.valueOf(e.getDate()));
            tableModel.addRow(row);
        }
    }

    // MODIFIES: this
    // EFFECTS: prints the log
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }
}

