package com.example.budgetsurvival

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.util.UUID

const val expensesFileName = "expenses.json"
const val settingsFileName = "settings.json"
fun loadExpenses(context: Context): List<Expense> {
    return try {
        val json = context.openFileInput(expensesFileName)
            .bufferedReader()
            .use { it.readText() }

        val array = JSONArray(json)

        List(array.length()) { index ->
            val item = array.getJSONObject(index)
            Expense(
                id = if(item.has("id")) {
                    UUID.fromString(item.getString("id"))
                } else {
                    UUID.randomUUID()
                },
                title = item.getString("title"),
                date = LocalDate.parse(
                    item.optString("date", LocalDate.now().toString())
                ),
                amount = item.getInt("amount"),
                status = ExpenseStatus.valueOf(
                    item.optString("status", ExpenseStatus.ACTUAL.name)
                ),
                medium = ExpenseMedium.valueOf(
                    item.optString("medium", ExpenseMedium.CARD.name)
                ),
                scale = BudgetBucket.valueOf(
                    item.optString("scale", BudgetBucket.DAILY.name)
                )
            )
        }
    } catch(e: Exception) {
        listOf(
            Expense(
                title="Телефон",
                date = LocalDate.now(),
                amount = 2_500,
                status = ExpenseStatus.ACTUAL,
                medium = ExpenseMedium.CARD,
                scale = BudgetBucket.DAILY
            )
        )
    }
}

fun saveExpenses(context: Context, expenses: List<Expense>){
    val array = JSONArray()
    expenses.forEach { expense ->
        val item = JSONObject()
        item.put("id", expense.id.toString())
        item.put("title", expense.title)
        item.put("date", expense.date.toString())
        item.put("amount", expense.amount)
        item.put("status", expense.status.name)
        item.put("medium", expense.medium.name)
        item.put("scale", expense.scale.name)
        array.put(item)
    }
    context.openFileOutput(expensesFileName, Context.MODE_PRIVATE)
        .bufferedWriter()
        .use { it.write(array.toString()) }

}

fun loadSettings(context: Context): BudgetSettings {
    return try {
        val json = context.openFileInput(settingsFileName)
            .bufferedReader()
            .use { it.readText() }

        val record = JSONObject(json)

        BudgetSettings(
            initialBudget = record.getInt("initialBudget"),
            endDate = LocalDate.parse(record.getString("endDate"))
        )
    } catch (e: Exception) {
        BudgetSettings(
            initialBudget = 35_000,
            endDate = LocalDate.of(2026, 6, 23)
        )
    }
}

fun saveSettings(context: Context, settings: BudgetSettings) {
    val obj = JSONObject()
    obj.put("initialBudget", settings.initialBudget)
    obj.put("endDate", settings.endDate.toString())
    context.openFileOutput(settingsFileName, Context.MODE_PRIVATE)
        .bufferedWriter()
        .use { it.write(obj.toString())}
}