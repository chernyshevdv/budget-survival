package dev.chernyshev.budgetsurvival

import java.util.UUID

sealed class Screen(val route: String) {
    data object Budget: Screen("budget")
    data object AddExpense: Screen("add-expense")
    data object EditExpense: Screen("edit-expense/{expenseId}") {
        fun createRoute(expenseId: UUID): String = "edit-expense/$expenseId"
    }
    data object BudgetSettings: Screen("budget-settings")
}