package com.example.budgetsurvival

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class BudgetSummary(
    val actualSpent: Int,
    val plannedAmount: Int,
    val freeAmount: Int,
    val daysLeft: Int,
    val perDay: Int,
    val todaySpent: Int,
    val todayRemaining: Int
)

fun calculateBudgetSummary(
    expenses: List<Expense>,
    settings: BudgetSettings,
    today: LocalDate = LocalDate.now()
): BudgetSummary {
    val daysLeft = ChronoUnit.DAYS.between(
        today, settings.endDate
    ).toInt() + 1
    val actualSpent = expenses
        .filter { it.status == ExpenseStatus.ACTUAL }
        .sumOf { it.amount }
    val todaySpent = expenses
        .filter {
            it.status == ExpenseStatus.ACTUAL
                    && it.scale == BudgetBucket.DAILY
                    && it.date == today }
        .sumOf { it.amount }
    val plannedAmount = expenses
        .filter { it.status == ExpenseStatus.PLANNED }
        .sumOf { it.amount }
    val freeAmount = settings.initialBudget - actualSpent - plannedAmount
    val perDay = if (daysLeft > 0) freeAmount / daysLeft else 0
    val todayRemaining = perDay - todaySpent

    return BudgetSummary(
        actualSpent, plannedAmount, freeAmount, daysLeft,
        perDay, todaySpent, todayRemaining
    )
}