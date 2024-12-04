package com.only.flobizassignment.presentation.ui.navigation

sealed class Routes(val routes: String) {

    data object LoginScreen : Routes("login_screen")

    data object DashboardScreen : Routes("dash_screen")

    data object RecordExpenseScreen : Routes("record_screen")

    data object ExpenseDetailScreen : Routes("expense_detail_screen")

    data object SettingsScreen : Routes("settings_screen")
}