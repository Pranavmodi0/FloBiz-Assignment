package com.only.flobizassignment.presentation.ui.navigation

sealed class Routes(val routes: String) {

    data object LoginScreen : Routes("login_screen")

    data object DashboardScreen : Routes("dash_screen")

    data object SettingsScreen : Routes("settings_screen")
}