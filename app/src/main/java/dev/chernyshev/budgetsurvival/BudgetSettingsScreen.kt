package dev.chernyshev.budgetsurvival

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSettingsScreen(
    settings: BudgetSettings,
    onBack: () -> Unit,
    onSave: (BudgetSettings) -> Unit
) {
    var textInitialBudget by remember { mutableStateOf(settings.initialBudget.toString()) }
    var endDate by remember { mutableStateOf( settings.endDate ) }

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
            DatePickerField(
                value = settings.endDate,
                onValueChange = { endDate = it },
                label = "Конец периода",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    buildSettings(textInitialBudget, endDate)
                        ?.let(onSave)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}

fun buildSettings(initialBudgetTxt: String, endDate: LocalDate): BudgetSettings? {
    val initialBudget = initialBudgetTxt.toIntOrNull()

    if (initialBudget != null && initialBudget >0)
        return BudgetSettings(initialBudget, endDate)

    return null
}