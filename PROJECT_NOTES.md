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

### 4. Language and currency settings

Extend BudgetSettings.

Possible additions:

    val language: AppLanguage
    val currency: String

Currency is intentionally simple for now: `RSD`, `$`, `€`, etc.

No exchange rates, currency conversion, or advanced formatting are planned.

## Later

### 5. Repository abstraction

Introduce repository interfaces while keeping JSON storage.

Possible interfaces:

    interface ExpenseRepository
    interface SettingsRepository

### 6. BudgetPeriod domain model

Introduce a real budget period entity.

Expected benefit:

* Multiple budget periods.
* Historical tracking.
* Foundation for carry-over logic.

### 7. Room migration

Room should be introduced before multiple related entities become part of the domain model.

Likely triggers:

* BudgetPeriod
* Carry-over expenses
* Recurring obligations

### 8. Carry expenses to future periods

Blocked by:

* BudgetPeriod
* Room migration

### 9. Recurring obligations

Blocked by:

* BudgetPeriod
* Room migration

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
