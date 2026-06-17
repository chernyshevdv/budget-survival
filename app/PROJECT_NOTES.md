# Budget Survival - Project Notes

## Current Status

The application is actively used for personal budget tracking.

Main workflow works:

- Load settings
- Load expenses
- Add expense
- Save expense
- Budget calculations
- Navigation between screens

---

# Domain Model

## Expense

Fields:

- title
- date
- amount
- status
- medium
- scale

### ExpenseStatus

- ACTUAL
- PLANNED

### ExpenseMedium

- CASH
- CARD

### BudgetBucket

- DAILY
- OBLIGATION

Purpose:

Daily expenses affect daily allowance.

Obligations affect budget but do not affect today's allowance.

Examples:

Daily:

- Food
- Coffee
- Transport

Obligation:

- Infostan
- Yettel
- Rent
- Subscriptions

---

# Budget Settings

## BudgetSettings

Fields:

- initialBudget
- endDate

Stored in:

```text
settings.json
```

---

# Budget Calculations

Implemented in:

```kotlin
BudgetSummary.kt
```

### Calculated Values

- actualSpent
- plannedAmount
- freeAmount
- daysLeft
- perDay
- todaySpent
- todayRemaining

---

# Application Structure

## BudgetApp

Responsibilities:

- Own application state
- Load/save data
- Coordinate navigation
- Open dialogs

## BudgetScreen

Responsibilities:

- Main budget screen
- Budget overview
- Today's overview
- Expense list

## ExpenseFormScreen

Responsibilities:

- Create expense
- Validate input
- Build Expense object

---

# Persistence

Implemented in:

```text
Storage.kt
```

Current files:

- expenses.json
- settings.json

---

# UI

## Budget Screen

Sections:

- Period
- Today
- Expenses

## Navigation

Screens:

- BudgetScreen
- ExpenseFormScreen

Navigation is implemented using Navigation Compose.

---

# Technical Debt

## High Priority

- Reuse ExpenseFormScreen for editing
- Remove AddExpenseDialog
- Move remaining user-facing strings to strings.xml

## Medium Priority

- Package structure
- DatePicker
- Better validation feedback

## Future

- Recurring obligations
- Budget periods
- Statistics
- Room (if needed)
- ViewModel (if needed)

---

# Design Principles

### State Hoisting

Data flows down.

Events flow up.

### Pragmatic Architecture

No architecture before pain.

Refactor only when duplication or complexity becomes visible.

### Useful First

The application should remain useful throughout the learning process.