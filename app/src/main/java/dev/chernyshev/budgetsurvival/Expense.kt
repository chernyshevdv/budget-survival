package dev.chernyshev.budgetsurvival

import java.time.LocalDate
import java.util.UUID


enum class ExpenseStatus {
    PLANNED,
    ACTUAL
}

enum class ExpenseMedium {
    CASH,
    CARD
}

enum class BudgetBucket {
    DAILY,
    OBLIGATION
}
data class Expense (
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val date: LocalDate,
    val amount: Int,
    val status: ExpenseStatus,
    val medium: ExpenseMedium,
    val scale: BudgetBucket
)

fun Expense.isPlan(): Boolean {
    return status == ExpenseStatus.PLANNED
}
