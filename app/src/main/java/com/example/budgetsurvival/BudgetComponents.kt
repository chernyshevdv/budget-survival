package com.example.budgetsurvival

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp

@Composable
fun BudgetProgressBar(
    actualSpent: Int,
    plannedAmount: Int,
    freeAmount: Int,
    daysLeft: Int
){
    val actualWeight = actualSpent.toFloat()
    val plannedWeight = plannedAmount.toFloat()
    val freeWeight = freeAmount.coerceAtLeast(0).toFloat()

    Card(modifier = Modifier.fillMaxWidth())
    {
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Период", style = MaterialTheme.typography.titleLarge)
            Text("Бюджет всего: ${(actualSpent + plannedAmount + freeAmount).rsd()}")
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            ) {
                if (actualWeight > 0){
                    Box(
                        modifier = Modifier
                            .weight(actualWeight)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.error)
                    )
                }
                if (plannedWeight > 0){
                    Box(
                        modifier = Modifier
                            .weight(plannedWeight)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.errorContainer)
                    )
                }
                if (freeWeight > 0){
                    Box(
                        modifier = Modifier
                            .weight(freeWeight)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }

            // Spacer(modifier = Modifier.height(16.dp))
            Text("Потрачено: ${actualSpent.rsd()}")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Schedule, contentDescription = "Запланировано")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Запланировано: ${plannedAmount.rsd()}")
            }
            Text("Осталось: ${freeAmount.rsd()} на $daysLeft дней")
        }
    }
}


@Composable
fun TodayCard(
    perDay: Int,
    todaySpent: Int,
    todayRemaining: Int
){
    val remainingColor = if (todayRemaining < 0)
        MaterialTheme.colorScheme.error
    else
        MaterialTheme.colorScheme.primary

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Сегодня",
                style= MaterialTheme.typography.titleLarge
            )
            Text("Дневной лимит: ${perDay.rsd()}")
            Text("Потрачено: ${todaySpent.rsd()}")
            Text(
                "Осталось на сегодня: ${todayRemaining.rsd()}",
                style= MaterialTheme.typography.titleLarge,
                color = remainingColor
            )
        }
    }
}