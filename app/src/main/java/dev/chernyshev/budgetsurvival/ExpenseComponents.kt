package dev.chernyshev.budgetsurvival

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID


@Composable
fun ExpenseRow(
    expense: Expense,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onDelete: () -> Unit
) {
    val mmddFormatter = DateTimeFormatter.ofPattern("dd.MM")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(modifier = Modifier.weight(1f)){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (expense.isPlan()) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Запланировано"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(expense.title)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        expense.date.format(mmddFormatter),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "•",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = expense.medium.icon,
                        contentDescription = expense.medium.name,
                        modifier = Modifier.width(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        expense.medium.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Text(expense.amount.rsd())
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить расход"
                )
            }

        }
    }
}

@Composable
fun ExpenseList(
    modifier: Modifier = Modifier,
    expenses: List<Expense>,
    onEdit: (UUID) -> Unit,
    onDelete: (UUID) -> Unit,
    onMarkActual: (UUID) -> Unit
){
    Text("Расходы", style=MaterialTheme.typography.titleLarge)
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(expenses) { item ->
            ExpenseRow(
                expense = item,
                onClick = {
                    onEdit(item.id)
                },
                onLongClick = {
                    if (item.status == ExpenseStatus.PLANNED)
                        onMarkActual(item.id)
                },
                onDelete = { onDelete(item.id) }
            )
        }
    }
}

@Composable
fun DatePickerField(
    value: LocalDate,
    onValueChange: (LocalDate) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = value.toEpochMillis()
    )

    OutlinedTextField(
        value = value.toString(),
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        singleLine = true,
        modifier = Modifier.clickable {isDialogOpen = true },
        trailingIcon = {
            IconButton(onClick = {isDialogOpen = true}) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Выбрать дату"
                )
            }
        }
    )

    if (isDialogOpen){
        DatePickerDialog(
            onDismissRequest = { isDialogOpen = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.toLocalDate()?.let(onValueChange)
                        isDialogOpen = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDialogOpen = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun LocalDate.toEpochMillis(): Long =
    atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

private fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

