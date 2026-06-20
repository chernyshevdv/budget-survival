# Budget Survival - Project Notes

[Roadmap / Backlog](#roadmap--backlog)

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

# Roadmap / Backlog

## Next

- [ ] 1. [Expense ID instead of list index](#1-expense-id-instead-of-list-index)
- [ ] 2. [DatePicker](#2-datepicker)
- [ ] 3. [Amount calculator for composite expenses](#3-amount-calculator-for-composite-expenses)
- [ ] 4. [i18n / string resources cleanup](#4-i18n--string-resources-cleanup)


### 1. Expense ID instead of list index

Current edit/delete/mark-actual flow should move from list indexes to stable expense identifiers.

Target model:

```kotlin
data class Expense(
    val id: String,
    val title: String,
    val date: LocalDate,
    val amount: Int,
    val status: ExpenseStatus,
    val medium: ExpenseMedium,
    val scale: BudgetBucket
)
```

Target routes and events:

```text
edit-expense/{expenseId}
deleteExpense(expenseId)
updateExpense(updatedExpense)
markPlannedAsActual(expenseId)
```

Expected benefit:

- Expense operations no longer depend on visual list order.
- Future filtering/sorting becomes safer.
- Navigation arguments become domain-oriented instead of UI-list-oriented.

### 2. DatePicker

Replace manual date text input with a proper date picker.

Expected benefit:

- Better UX.
- Less invalid input.
- Good practice with form state and Material components.

### 3. Amount calculator for composite expenses

When entering an expense amount, the amount field could open or embed a small calculator component.

Use case:

- The user bought several things during the day in the same category.
- Example: two pharmacy visits in one day.
- Instead of entering two separate expenses, the user wants to enter one expense and calculate the total in-place.

Possible UX options:

- Calculator icon near the amount field.
- Bottom sheet calculator.
- Inline expression input, for example `320 + 450 + 120`.
- Result is written back into the amount field.

Expected benefit:

- Faster entry of composite expenses.
- Less context switching to an external calculator app.
- Good practice with reusable UI components and temporary form state.

### 4. i18n / string resources cleanup

Move remaining user-facing strings to Android string resources.
Prepare three languages.

Target languages:

- English
- Russian
- Serbian

Expected benefit:

- Cleaner UI code.
- Easier localization.
- Less hardcoded text in composables.

## Later

### 5. BudgetPeriod domain model

Introduce a real budget period entity instead of treating settings as the whole budget lifecycle.

Possible model:

```kotlin
data class BudgetPeriod(
    val id: String,
    val title: String,
    val initialBudget: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
)
```

Expense may later reference a period:

```kotlin
val periodId: String
```

Expected benefit:

- Multiple budget periods.
- Better historical tracking.
- Foundation for period-to-period carry-over logic.

### 6. Carry expenses to future periods

Some expenses should be marked as reusable in future periods.

Possible field:

```kotlin
val carryToFuturePeriods: Boolean = false
```

Expected benefit:

- Obligations like rent, internet, phone, subscriptions can be copied into future periods.
- This should come after BudgetPeriod exists.

### 7. Recurring obligations

Recurring obligations should be introduced only after BudgetPeriod and carry-over rules become clear.

Examples:

- Rent
- Internet
- Phone
- Subscriptions

## Future / Optional

- Statistics screen
- Better validation feedback
- Package structure cleanup
- Room, if JSON storage becomes painful
- ViewModel, if BudgetApp becomes too large

---

# Technical Debt

## High Priority

- Reuse ExpenseFormScreen for editing
- Remove AddExpenseDialog
- Move remaining user-facing strings to strings.xml

## Medium Priority

- Package structure
- DatePicker
- Amount calculator for composite expenses
- Better validation feedback

## Future

- Budget periods
- Carry-over expenses
- Recurring obligations
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
