package dev.chernyshev.budgetsurvival

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseFormScreen(
    expense: Expense? = null,
    onBack: () -> Unit,
    onSave: (Expense) -> Unit
) {
    var title by remember { mutableStateOf(expense?.title ?: "") }
    var amountText by remember { mutableStateOf(expense?.amount?.toString() ?: "") }
    var amount by remember { mutableStateOf<Int?>(expense?.amount ) }
    var date by remember { mutableStateOf(expense?.date ?: LocalDate.now())}
    var isPlanned by remember { mutableStateOf(expense?.status == ExpenseStatus.PLANNED) }
    var isCash by remember { mutableStateOf(expense?.medium == ExpenseMedium.CASH) }
    var isObligation by remember { mutableStateOf(expense?.scale == BudgetBucket.OBLIGATION) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (expense == null) "Новый расход" else "Редактировать расход") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Что купил?") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            DatePickerField(
                value=date,
                onValueChange={ date = it },
                label = "Когда?",
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(12.dp))
//            OutlinedTextField(
//                value = amountText,
//                onValueChange = { amountText = it },
//                label = { Text("Сколько?") },
//                singleLine = true
//            )
            MoneyField(
                value = amount,
                onValueChange = { amount = it },
                label = "Сколько?",
                modifier = Modifier.fillMaxWidth()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isPlanned,
                    onCheckedChange = {
                        isPlanned = it
                    }
                )
                Text("Запланированный расход")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isCash,
                    onCheckedChange = {
                        isCash = it
                    }
                )
                Text("Наличка")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isObligation,
                    onCheckedChange = {
                        isObligation = it
                    }
                )
                Text("Уровень месяца")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    buildExpense(
                        existingExpense = expense,
                        title=title,
                        amount=amount,
                        date= date,
                        isPlanned=isPlanned,
                        isCash = isCash,
                        isObligation=isObligation
                    )?.let(onSave)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}

private fun buildExpense(
    existingExpense: Expense?,
    title: String,
    amount: Int?,
    date: LocalDate,
    isPlanned: Boolean,
    isCash: Boolean,
    isObligation: Boolean
): Expense? {
    // Builds expense with the same id, if it exists
    if (
        title.isBlank() ||
        amount == null ||
        amount <= 0
    ) return null

    val status = if (isPlanned) ExpenseStatus.PLANNED
    else ExpenseStatus.ACTUAL
    val medium = if (isCash) ExpenseMedium.CASH
    else ExpenseMedium.CARD
    val scale = if (isObligation) BudgetBucket.OBLIGATION
    else BudgetBucket.DAILY

    return existingExpense?.copy(
        // leave id from the existingExpense
        title=title.trim(),
        date = date,
        amount = amount,
        status = status,
        medium = medium,
        scale = scale
    ) ?: Expense(
        title=title.trim(),
        date = date,
        amount = amount,
        status = status,
        medium = medium,
        scale = scale)

}