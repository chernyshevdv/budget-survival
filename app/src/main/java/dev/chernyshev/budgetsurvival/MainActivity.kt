package dev.chernyshev.budgetsurvival

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.chernyshev.budgetsurvival.theme.BudgetSurvivalTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstance: Bundle?){
        super.onCreate(savedInstance)
        enableEdgeToEdge()

        setContent {
            BudgetSurvivalTheme {
                BudgetApp()
            }
        }
    }
}


@Preview
@Composable
fun BudgetAppPreview(){
    BudgetSurvivalTheme {
        BudgetApp()
    }
}
