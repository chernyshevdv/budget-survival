# Budget Survival - Learnings

## Kotlin

### Data Classes

Used for:

- Expense
- BudgetSettings
- BudgetSummary

### Extension Functions

Examples:

```kotlin
ExpenseMedium.display()
Int.rsd()
```

### Nullable Types

```kotlin
Expense?
```

Meaning:

- Expense
- or null

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
```

Mental model:

```cpp
void func()

void func(Expense)

void func(String)
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

- BudgetScreen
- ExpenseFormScreen

### Layout System

Core concepts:

- Row
- Column
- Box
- Spacer

Important modifiers:

- fillMaxWidth()
- fillMaxHeight()
- weight()
- padding()
- size()

### Material 3

Implemented:

- TopAppBar
- FloatingActionButton
- Material Icons
- Cards
- Typography

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

---

# Personal Conclusions

The most important discovery:

> Screen = Function of State.

The second most important discovery:

> Architecture should solve existing problems, not hypothetical ones.

And finally:

> Compose is not terrible.
>
> It just should not be learned through Hilt.