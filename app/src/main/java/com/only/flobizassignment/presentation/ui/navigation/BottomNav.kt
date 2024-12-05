package com.only.flobizassignment.presentation.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.only.flobizassignment.R
import com.only.flobizassignment.presentation.ui.DashboardScreen
import com.only.flobizassignment.presentation.ui.ExpenseDetailScreen
import com.only.flobizassignment.presentation.ui.LoginScreen
import com.only.flobizassignment.presentation.ui.RecordExpenseScreen
import com.only.flobizassignment.presentation.ui.SettingsScreen
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.colorSecondary

private lateinit var auth: FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNav() {

    auth = Firebase.auth

    val list = listOf(
        NavItem(
            title = "Dashboard",
            selectedIcon = R.drawable.home,
            Routes.DashboardScreen.routes
        ),
        NavItem(
            title = "Settings",
            selectedIcon = R.drawable.settings,
            Routes.SettingsScreen.routes
        ),
    )

    val navController = rememberNavController()

    val startDestination = if (auth.currentUser != null) {
        Routes.DashboardScreen.routes
    } else {
        Routes.LoginScreen.routes
    }

    val backStackEntry by navController.currentBackStackEntryAsState()

    var showBottomBar by rememberSaveable {
        mutableStateOf(true)
    }

    showBottomBar = when (backStackEntry?.destination?.route) {
        Routes.LoginScreen.routes -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        bottomBar = {
            if (showBottomBar) {
                Box(
//                    modifier = Modifier.padding(
//                        bottom = WindowInsets.navigationBars.asPaddingValues()
//                            .calculateBottomPadding())
                ) {
                    NavigationBar(
                        containerColor = Color.White,
                    ) {
                        list.forEachIndexed { _, item ->
                            NavigationBarItem(
                                selected = backStackEntry?.destination?.route?.startsWith(item.route)
                                    ?: false,
                                onClick = {
                                    navController.navigate(item.route)
                                },
                                colors = NavigationBarItemColors(
                                    selectedIconColor = Color.White,
                                    selectedTextColor = Color.White,
                                    selectedIndicatorColor = Color.White,
                                    unselectedIconColor = Color.White,
                                    unselectedTextColor = Color.White,
                                    disabledIconColor = Color.White,
                                    disabledTextColor = Color.White,
                                ),
                                icon = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        Image(
                                            painter = painterResource(id = item.selectedIcon),
                                            contentDescription = item.title,
                                            modifier = Modifier
                                                .size(25.dp)
                                                .graphicsLayer {
                                                    alpha =
                                                        if (backStackEntry?.destination?.route?.startsWith(
                                                                item.route
                                                            ) == true
                                                        ) 1f else 0.5f
                                                },
                                            colorFilter = if (backStackEntry?.destination?.route?.startsWith(
                                                    item.route
                                                ) == true
                                            ) null else ColorFilter.tint(Color.Gray)
                                        )
                                        Text(
                                            text = item.title,
                                            fontSize = 10.sp,
                                            color = if (backStackEntry?.destination?.route?.startsWith(
                                                    item.route
                                                ) == true
                                            ) colorSecondary else Color.LightGray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                },
                            )
                        }
                    }
                }
            }
        },
        content = { padding ->

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(padding),
            ) {
                composable(Routes.LoginScreen.routes) {
                    LoginScreen(
                        navController = rememberNavController()
                    )
                }
                composable(Routes.DashboardScreen.routes) {
                    DashboardScreen()
                }
                composable(Routes.RecordExpenseScreen.routes) {
                    RecordExpenseScreen()
                }
                composable(Routes.ExpenseDetailScreen.routes) {
                    ExpenseDetailScreen()
                }
                composable(Routes.SettingsScreen.routes) {
                    SettingsScreen()
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun BottomNavPreview() {
    BottomNav()
}