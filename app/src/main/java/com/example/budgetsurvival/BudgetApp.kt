package com.example.budgetsurvival

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun BudgetApp(){
    val context = androidx.compose.ui.platform.LocalContext.current

    var settings by remember {
        mutableStateOf(loadSettings(context))
    }
    var expenses by remember {
        mutableStateOf(loadExpenses(context))
    }

    val navController = rememberNavController()

    val addExpense: (expense: Expense) -> Unit = { expense ->
        expenses = expenses + expense
        saveExpenses(context,expenses)
    }

    val updateExpense: (index: Int, updatedExpense: Expense) -> Unit = { index, expense ->
        expenses = expenses.toMutableList().also {
            it[index] = expense
        }
        saveExpenses(context, expenses)
    }

    val deleteExpense: (index: Int) -> Unit = { index ->
        expenses = expenses.toMutableList().also {
            it.removeAt(index)
        }
        saveExpenses(context, expenses)
    }

    val mutatePlannedToActual: (index: Int) -> Unit = {index ->
        expenses = expenses.toMutableList().also {
            val expense = it[index].copy(
                status = ExpenseStatus.ACTUAL
            )
            it[index] = expense
        }
        saveExpenses(context, expenses)
    }

    val updateSettings: (updatedSettings: BudgetSettings) -> Unit = { updatedSettings ->
        settings = updatedSettings.also {
            saveSettings(context, it)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Budget.route
    ) {
        composable(Screen.Budget.route) {
            BudgetScreen(
                expenses,
                settings,

                onAddExpenseClick = {
                    navController.navigate(Screen.AddExpense.route)
                },
                onSettingsClick = { navController.navigate(Screen.BudgetSettings.route) },
                onEditExpenseClick = { index ->
                    navController.navigate(Screen.EditExpense.createRoute(index))
                },
                onMarkActual = mutatePlannedToActual,
                onDelete = deleteExpense
            )
        }
        composable(
            route = Screen.EditExpense.route,
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: return@composable
            val expense = expenses.getOrNull(index) ?: return@composable

            ExpenseFormScreen(
                expense = expense,
                onBack = {
                    navController.popBackStack()
                },
                onSave = { updatedExpense ->
                    updateExpense(index, updatedExpense)
                    navController.popBackStack()
                }
            )
        }
        composable(route = Screen.AddExpense.route){
            ExpenseFormScreen(
                expense = null,
                onBack = { navController.popBackStack() },
                onSave = { expense ->
                    addExpense(expense)
                    navController.popBackStack()
                }
            )
        }
        composable(route= Screen.BudgetSettings.route){
            BudgetSettingsScreen(
                settings = settings,
                onBack = { navController.popBackStack() },
                onSave = { newBudgetSettings ->
                    updateSettings(newBudgetSettings)
                    navController.popBackStack()
                }
            )
        }
    }
}