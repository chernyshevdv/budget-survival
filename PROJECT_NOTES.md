# Budget Survival - Project Notes

[Roadmap / Backlog](#roadmap--backlog)

## Current Status

The application is actively used for personal budget tracking.

Main workflow works:

* Load settings
* Load expenses
* Add expense
* Edit expense
* Delete expense
* Save expense
* Budget calculations
* Navigation between screens

Recent improvements:

* Stable expense identifiers
* Reusable DatePicker component
* Optional release signing for local development

---

# Domain Model

## Expense

Fields:

* id
* title
* date
* amount
* status
* medium
* scale

### ExpenseStatus

* ACTUAL
* PLANNED

### ExpenseMedium

* CASH
* CARD

### BudgetBucket

* DAILY
* OBLIGATION

Purpose:

Daily expenses affect daily allowance.

Obligations affect budget but do not affect today's allowance.

Examples:

Daily:

* Food
* Coffee
* Transport

Obligation:

* Infostan
* Yettel
* Rent
* Subscriptions

---

# Budget Settings

## BudgetSettings

Fields:

* initialBudget
* endDate

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

* actualSpent
* plannedAmount
* freeAmount
* daysLeft
* perDay
* todaySpent
* todayRemaining

---

# Application Structure

## BudgetApp

Responsibilities:

* Own application state
* Load/save data
* Coordinate navigation
* Open screens

## BudgetScreen

Responsibilities:

* Main budget screen
* Budget overview
* Today's overview
* Expense list

## ExpenseFormScreen

Responsibilities:

* Create expense
* Edit expense
* Validate input
* Build Expense object

## BudgetSettingsScreen

Responsibilities:

* Configure budget settings
* Configure budget end date

---

# Persistence

Implemented in:

```text
Storage.kt
```

Current files:

* expenses.json
* settings.json

---

# UI

## Budget Screen

Sections:

* Period
* Today
* Expenses

## Navigation

Screens:

* BudgetScreen
* ExpenseFormScreen
* BudgetSettingsScreen

Navigation is implemented using Navigation Compose.

## Shared Components

### DatePickerField

Reusable Material 3 date picker component.

Used in:

* ExpenseFormScreen
* BudgetSettingsScreen

### Date Extensions

Reusable helpers:

```kotlin
fun LocalDate.toEpochMillis(): Long

fun Long.toLocalDate(): LocalDate
```

---

# Roadmap / Backlog

## Next

* [ ] 1. Amount calculator for composite expenses
* [ ] 2. i18n / string resources cleanup

### 1. Amount calculator for composite expenses

When entering an expense amount, the amount field could open or embed a small calculator component.

Use case:

* The user bought several things during the day in the same category.
* Example: two pharmacy visits in one day.
* Instead of entering two separate expenses, the user wants to enter one expense and calculate the total in-place.

Possible UX options:

* Calculator icon near the amount field.
* Bottom sheet calculator.
* Inline expression input, for example `320 + 450 + 120`.
* Result is written back into the amount field.

Expected benefit:

* Faster entry of composite expenses.
* Less context switching to an external calculator app.
* Good practice with reusable UI components and temporary form state.

### 2. i18n / string resources cleanup

Move remaining user-facing strings to Android string resources.

Target languages:

* English
* Russian
* Serbian

Expected benefit:

* Cleaner UI code.
* Easier localization.
* Less hardcoded text in composables.

## Later

### 3. BudgetPeriod domain model

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

* Multiple budget periods.
* Better historical tracking.
* Foundation for period-to-period carry-over logic.

### 4. Carry expenses to future periods

Some expenses should be marked as reusable in future periods.

Possible field:

```kotlin
val carryToFuturePeriods: Boolean = false
```

### 5. Recurring obligations

Recurring obligations should be introduced only after BudgetPeriod and carry-over rules become clear.

Examples:

* Rent
* Internet
* Phone
* Subscriptions

## Future / Optional

* Statistics screen
* Better validation feedback
* Package structure cleanup
* Room, if JSON storage becomes painful
* ViewModel, if BudgetApp becomes too large

---

# Technical Debt

## High Priority

* Move remaining user-facing strings to strings.xml

## Medium Priority

* Amount calculator for composite expenses
* Better validation feedback

## Future

* Budget periods
* Carry-over expenses
* Recurring obligations
* Statistics
* Room (if needed)
* ViewModel (if needed)

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
