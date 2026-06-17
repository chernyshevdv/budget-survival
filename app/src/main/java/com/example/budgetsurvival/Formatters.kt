package com.example.budgetsurvival

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun ExpenseMedium.display(): String =
    when(this){
        ExpenseMedium.CASH -> stringResource(R.string.expense_medium_cash)
        ExpenseMedium.CARD -> stringResource(R.string.expense_medium_card)
    }


val ExpenseMedium.icon
    get() = when(this) {
        ExpenseMedium.CASH -> Icons.Default.Payments
        ExpenseMedium.CARD -> Icons.Default.CreditCard
    }

fun Int.rsd(): String = "%,d RSD".format(this)