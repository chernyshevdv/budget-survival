package com.example.budgetsurvival

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSettingsScreen(
    settings: BudgetSettings,
    onBack: () -> Unit,
    onSave: (BudgetSettings) -> Unit
) {
    var textInitialBudget by remember { mutableStateOf(settings.initialBudget.toString()) }
    var textEndDate by remember { mutableStateOf(settings.endDate.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Settings") },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize()
        ){
            OutlinedTextField(
                value = textInitialBudget,
                onValueChange = { textInitialBudget = it },
                label = { Text("Бюджет") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = textEndDate,
                onValueChange = { textEndDate = it },
                label = { Text("Конец периода") },
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    buildSettings(textInitialBudget, textEndDate)
                        ?.let(onSave)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}

fun buildSettings(initialBudgetTxt: String, endDateTxt: String): BudgetSettings? {
    val initialBudget = initialBudgetTxt.toIntOrNull()
    val endDate = try {
        LocalDate.parse(endDateTxt)
    } catch (e: DateTimeParseException){
        null
    }

    if (initialBudget != null && initialBudget >0 && endDate != null)
        return BudgetSettings(initialBudget, endDate)

    return null
}