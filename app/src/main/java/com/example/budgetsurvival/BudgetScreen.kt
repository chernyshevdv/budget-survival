package com.example.budgetsurvival

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    expenses: List<Expense>,
    settings: BudgetSettings,
    onAddExpenseClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onEditExpenseClick: (UUID) -> Unit,
    onMarkActual: (UUID) -> Unit,
    onDelete: (UUID) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BudgetSurvival") },
                actions = {
                    IconButton(onClick = onSettingsClick)
                    {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Бюджетные настройки"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить расход"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize()
        ){
            val today = LocalDate.now()
            val budget = calculateBudgetSummary(expenses, settings, today)

            BudgetProgressBar(
                budget.actualSpent, budget.plannedAmount,
                budget.freeAmount, budget.daysLeft
            )
            Spacer(modifier = Modifier.height(8.dp))
            TodayCard(budget.perDay, budget.todaySpent, budget.todayRemaining)
            Spacer(modifier = Modifier.height(8.dp))
            ExpenseList(
                modifier = Modifier.weight(1f),
                expenses.sortedByDescending { it.date },
                onEdit = onEditExpenseClick,
                onDelete = onDelete,
                onMarkActual = onMarkActual
            )
        }
    }
}