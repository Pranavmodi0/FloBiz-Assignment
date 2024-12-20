package com.only.flobizassignment.presentation.ui.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.gson.Gson
import com.only.flobizassignment.R
import com.only.flobizassignment.data.Expenses
import com.only.flobizassignment.presentation.ui.DashboardScreen
import com.only.flobizassignment.presentation.ui.ExpenseDetailScreen
import com.only.flobizassignment.presentation.ui.LoginScreen
import com.only.flobizassignment.presentation.ui.RecordExpenseScreen
import com.only.flobizassignment.presentation.ui.SettingsScreen
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.colorSecondary
import kotlinx.coroutines.launch

const val WED_ID = "168578407811-ainvthvsebojabckikd8qb9cek9j04nt.apps.googleusercontent.com"
private lateinit var auth: FirebaseAuth
private lateinit var user: FirebaseUser

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNav(
    navController: NavHostController
) {

    auth = Firebase.auth

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

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

    val backStackEntry by navController.currentBackStackEntryAsState()

    var loading by remember {
        mutableStateOf(false)
    }

    var showBottomBar by rememberSaveable {
        mutableStateOf(true)
    }

    showBottomBar = when (backStackEntry?.destination?.route) {
        Routes.DashboardScreen.routes -> true
        Routes.SettingsScreen.routes -> true
        else -> false
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        bottomBar = {
            if (showBottomBar) {

                Column (
                    modifier = Modifier.fillMaxWidth().padding(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
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
            }
        },
        content = { padding ->

            val startDestination = rememberSaveable {
                mutableStateOf(
                    if (auth.currentUser != null) {
                        Routes.DashboardScreen.routes
                    } else {
                        Routes.LoginScreen.routes
                    }
                )
            }

            val startDestinations = startDestination.value

            NavHost(
                navController = navController,
                startDestination = startDestinations,
                modifier = Modifier.padding(padding),
            ) {
                composable(Routes.LoginScreen.routes) {
                    LoginScreen(
                        loading = loading,
                        onClick = {
                            loading = true
                            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                                .setFilterByAuthorizedAccounts(false)
                                .setServerClientId(WED_ID)
                                .build()

                            val request = GetCredentialRequest.Builder()
                                .addCredentialOption(googleIdOption)
                                .build()

                            scope.launch {
                                try {
                                    val result = credentialManager.getCredential(
                                        request = request,
                                        context = context,
                                    )
                                    val credential = result.credential
                                    val googleIdTokenCredential = GoogleIdTokenCredential
                                        .createFrom(credential.data)
                                    val googleIdToken = googleIdTokenCredential.idToken

                                    val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)


                                    auth.signInWithCredential(firebaseCredential)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                scope.launch {
                                                    user = auth.currentUser!!
                                                    loading = false
                                                    navController.popBackStack()
                                                    navController.navigate("dash_screen")
                                                }
                                            }
                                        }

                                } catch (e: Exception) {
                                    loading = false
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                        .show()
                                    e.printStackTrace()
                                }
                            }
                        }
                    )
                }
                composable(
                    Routes.DashboardScreen.routes,
                ) {
                    DashboardScreen(
                        navController = navController
                    )
                }
                composable(Routes.RecordExpenseScreen.routes) {
                    RecordExpenseScreen(
                        navController,
                        auth = auth
                    )
                }
                composable(
                    route = Routes.ExpenseDetailScreen.routes,
                    arguments = listOf(navArgument("expenses") { type = NavType.StringType })
                ) { backStackEntry ->
                    val expenseJson = backStackEntry.arguments?.getString("expenses")
                    val expenses = Gson().fromJson(expenseJson, Expenses::class.java)
                    ExpenseDetailScreen(
                        expenses,
                        navController,
                        auth
                    )
                }
                composable(Routes.SettingsScreen.routes) {
                    SettingsScreen(
                        navController
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun BottomNavPreview() {
    BottomNav(
        navController = rememberNavController()
    )
}