package com.example.budgetsurvival

import java.time.LocalDate

data class BudgetSettings (
    val initialBudget: Int,
    val endDate: LocalDate
)