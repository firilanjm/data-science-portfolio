# Personal Expense Tracker
## Project Overview
Firila Najma Wahidah (28602753)

**What will application do?**

 Personal Expense Tracker application let users to keep track on their expenses, categorize them (home, food, utilities, entertainment, and other), and view their spending history. This application allows users to record an expense with details (description name, amount, category, and date). It will display a list of all recorded expenses and also can be sorted by category or date range. This application will help users in managing their financial transactions.

**Who will use it?**

This application is designed for individuals who want to take control of their personal finance, students who are learning to manage their money, and small business owners who need a simple budget tracker to manage business expenses without using complicated accounting tool.

**Why is this project of interest to you?**

This project is interesting for me because it is practical real-life application. As a student, I want to learn manage my financial habits for the first time. Moreover, building this application is a useful tool not only for learning to manage our money, but also improving our financial awareness.

## User Stories
- As a user, I want to be able to add an expense (with description name, amount, category, and date) to my personal expenses tracker.
- As a user, I want to be able to modify an existing expense in my personal expenses tracker.
- As a user, I want to be able to remove an existing expense from my personal expenses tracker.
- As a user, I want to be able to view all expenses history and the total amount of expenses .
- As a user, I want to be able to filter the expenses by category or date range, view the filtered expenses with the total amount of those expenses.
- As a user, I want to be able to save my personal expense tracker to file (if I so choose)
- As a user, I want to be able to be able to load my personal expense tracker from file (if I so choose)

# Instructions for End User

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by click on "Add expense" button in the top panel, then type the expense name, amount, choose category, and type the date range. Then click "Add expense" button.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" (view all of your expenses that have been added) by looking at the table in the main panel.
- You can remove an existing expense from your expense tracker by select an expense row to be removed then click "Remove expense" button in the top panel (you can only select one expense to be removed at one time).
- You can filter the expenses by category or date range in your expense tracker by click on "Filter expense" button in the top panel. If you want to filter by category, click "Filter by category" then select the category you want to filter and click "Filter" button. If you want to filter by date range, click, "Filter by date range", then type the start date and end date you want, then click "Filter" button.
- You can view all of expenses in your expense tracker **after filtering it** by click on "View all expense" button in the top panel.
- You can locate my visual component by looking at an icon that was added into each button in the top panel (add expense, remove expense, filter expense, view all expenses) and the icons in the pop up message of laoding and saving the data.
- You can save your expense to data by click "Save and Quit" on the pop-up message when you close the application.
- You can reload your expense from data by click "Load my expense list" on the pop-up message at the beginning when you open the application.

# Phase 4: Task 2
Representative Sample of Log Event:
- Tue Nov 26 10:45:41 PST 2024
View all expenses
- Tue Nov 26 10:45:43 PST 2024
Loaded your expenses from file
- Tue Nov 26 10:45:43 PST 2024
Added an expense: groceries, 150.0, Food, 2024-05-07
- Tue Nov 26 10:45:43 PST 2024
Added an expense: wifi, 40.0, Utilities, 2024-03-09
- Tue Nov 26 10:45:43 PST 2024
View all expenses
- Tue Nov 26 10:45:53 PST 2024
Added an expense: chocolate, 20.0, Other, 2024-08-07
- Tue Nov 26 10:46:01 PST 2024
Added an expense: rent, 800.0, Home, 2024-06-03
- Tue Nov 26 10:46:04 PST 2024
Filter the expenses by category: Home
- Tue Nov 26 10:46:08 PST 2024
Filter the expenses by category: Utilities
- Tue Nov 26 10:46:10 PST 2024
View all expenses
- Tue Nov 26 10:46:17 PST 2024
Filter the expenses by date range between 2024-09-01 and 2024-10-09
- Tue Nov 26 10:46:26 PST 2024
Filter the expenses by date range between 2024-01-09 and 2024-12-09
- Tue Nov 26 10:46:28 PST 2024
View all expenses
- Tue Nov 26 10:46:31 PST 2024
Removed an expense: chocolate, 20.0, Other, 2024-08-07
- Tue Nov 26 10:46:34 PST 2024
Removed an expense: wifi, 40.0, Utilities, 2024-03-09
- Tue Nov 26 10:46:36 PST 2024
Saved your expenses to file


# Phase 4: Task 3
If I had more time to work on the project, I would refactor by change the AddExpenseWindow and FilterExpenseWindow to extends ExpenseTrackerGUI, so they could inherit all needed methods. Because there is same behaviour after adding and filtering the expenses, which is displaying the expenses in the table. There is repetitive code blocks to perform display the expenses behaviour.

I would refactor this repetitive code blocks by extracting common functionality into utility method. Then, i only need to call the same method in  AddExpenseWindow and FilterExpenseWindow that inherited from ExpenseTrackerGUI. This would reduce redundancy and potential errors, as updates to the logic would only need to be made in one place. Thus, if this codes perform similar error handling, I would only need to fix the error in a method. Hence, there would be improvement in maintainability and readability of the code.
