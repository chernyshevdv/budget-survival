package com.example.budgetsurvival

sealed class Screen(val route: String) {
    data object Budget: Screen("budget")
    data object AddExpense: Screen("add-expense")
    data object EditExpense: Screen("edit-expense/{index}") {
        fun createRoute(index: Int): String = "edit-expense/$index"
    }
    data object BudgetSettings: Screen("budget-settings")
}