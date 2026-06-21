# Budget Survival - Learnings

## Kotlin

### Data Classes

Used for:

* Expense
* BudgetSettings
* BudgetSummary

### Extension Functions

Examples:

```kotlin
ExpenseMedium.display()
Int.rsd()
LocalDate.toEpochMillis()
Long.toLocalDate()
```

### Nullable Types

```kotlin
Expense?
```

Meaning:

* Expense
* or null

### Safe Calls

```kotlin
expense?.title
```

### let

```kotlin
expense?.let {
    ...
}
```

Meaning:

> Execute only when value is not null.

### Function Types

Examples:

```kotlin
() -> Unit

(Expense) -> Unit

(String) -> Unit

(LocalDate) -> Unit
```

Mental model:

```cpp
void func()

void func(Expense)

void func(String)

void func(LocalDate)
```

### Unit

Kotlin equivalent of:

```cpp
void
```

Unlike C++, Unit is a real type.

---

# Jetpack Compose

## Screen = Function of State

```kotlin
var expenses by remember { ... }
```

Changing state causes recomposition.

### State Hoisting

Components do not modify state directly.

Preferred approach:

```text
Data down
Events up
```

Example:

```kotlin
ExpenseList(
    expenses = expenses,
    onDelete = { ... }
)
```

### Navigation

First Navigation Compose experience.

Current screens:

* BudgetScreen
* ExpenseFormScreen
* BudgetSettingsScreen

### Layout System

Core concepts:

* Row
* Column
* Box
* Spacer

Important modifiers:

* fillMaxWidth()
* fillMaxHeight()
* weight()
* padding()
* size()

### Material 3

Implemented:

* TopAppBar
* FloatingActionButton
* Material Icons
* Cards
* Typography
* DatePicker
* DatePickerDialog

### Reusable Components

First reusable UI component:

```kotlin
DatePickerField
```

Used in:

* ExpenseFormScreen
* BudgetSettingsScreen

Learning:

> Extract a component after the second use case appears.

### DatePicker State

Interesting Compose pattern:

```text
DatePicker
    ↓
DatePickerState
    ↑
Callback
    ↑
Screen State
```

The DatePicker owns temporary UI state.

The screen owns business state.

Example:

```kotlin
DatePickerField(
    value = date,
    onValueChange = { date = it }
)
```

This is another example of:

```text
Data down
Events up
```

---

# Architectural Learnings

## Architecture Appears From Pain

Examples:

### BudgetSummary.kt

Extracted when calculations became too large.

### buildExpense()

Extracted when button handler became difficult to read.

### Navigation

Introduced when dialogs stopped being sufficient.

### DatePickerField

Extracted when date selection appeared in multiple screens.

---

# Build & Release Learnings

## Release Signing Should Not Block Development

A new machine failed to sync because release signing configuration depended on a private keystore.

Solution:

* Make release signing optional.
* Keep debug builds independent from release credentials.

Learning:

> Private release infrastructure should not be required for local development.

---

# Personal Conclusions

The most important discovery:

> Screen = Function of State.

The second most important discovery:

> Architecture should solve existing problems, not hypothetical ones.

The third:

> Reusable components are worth extracting only after duplication appears.

And finally:

> Compose is not terrible.
>
> It just should not be learned through Hilt.
