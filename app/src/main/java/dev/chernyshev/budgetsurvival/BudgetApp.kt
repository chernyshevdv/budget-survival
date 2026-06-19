package dev.chernyshev.budgetsurvival

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.time.LocalDate
import java.util.UUID

@Composable
fun BudgetApp(){
    val context = LocalContext.current

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

    val updateExpense: (Expense) -> Unit = { updatedExpense ->
        expenses = expenses.map {
            if (it.id == updatedExpense.id)
                updatedExpense
            else
                it
        }
        saveExpenses(context, expenses)
    }

    val deleteExpense: (UUID) -> Unit = { id ->
        expenses = expenses.filter {
            it.id != id
        }
        saveExpenses(context, expenses)
    }

    val mutatePlannedToActual: (UUID) -> Unit = { id ->
        expenses = expenses.map {
            if (it.id == id)
                it.copy(
                    status = ExpenseStatus.ACTUAL,
                    date = LocalDate.now()
                )
            else
                it
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
            arguments = listOf(navArgument("expenseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expenseIdString = backStackEntry.arguments
                ?.getString("expenseId") ?: return@composable

            val expenseId = try {
                UUID.fromString(expenseIdString)
            }catch (e: IllegalArgumentException) {
                return@composable
            }
            val expense = expenses.firstOrNull{ it.id == expenseId } ?: return@composable

            ExpenseFormScreen(
                expense = expense,
                onBack = {
                    navController.popBackStack()
                },
                onSave = { updatedExpense ->
                    updateExpense(updatedExpense)
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