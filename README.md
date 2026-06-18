# Budget Survival

Personal Android application for managing a limited-period budget.

The project serves two purposes:

1. Real-life budget tracking.
2. Learning Android development with Kotlin and Jetpack Compose.

## Features

### Expenses

- Add expense
- Delete expense
- Edit expense (in progress)
- Planned expenses
- Mark planned expense as actual
- Cash/Card payment methods
- Daily vs Obligation expense classification

### Budget Tracking

- Actual spent amount
- Planned amount
- Free amount
- Daily allowance
- Today's spending
- Today's remaining allowance

### Settings

- Configurable initial budget
- Configurable budget end date
- JSON persistence

## Technology

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- JSON file storage

Intentionally not used (yet):

- Room
- ViewModel
- Hilt
- Clean Architecture

The project follows a pragmatic approach:

> Build a useful application first. Introduce architecture only when a real problem appears.

## Current Architecture

### State

Application state is owned by `BudgetApp`.

Main state:

- expenses
- settings

### Navigation

- BudgetScreen
- ExpenseFormScreen

### Persistence

JSON files:

- expenses.json
- settings.json

### Business Logic

Budget calculations are isolated in:

```kotlin
calculateBudgetSummary()
```

## Future Plans

- Edit Expense
- DatePicker
- strings.xml cleanup
- Recurring obligations
- Budget periods
- Statistics screen