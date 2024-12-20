package com.only.flobizassignment.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.only.flobizassignment.R
import com.only.flobizassignment.data.Expenses
import com.only.flobizassignment.presentation.model.DashboardViewModel
import com.only.flobizassignment.presentation.ui.navigation.Routes
import com.only.flobizassignment.ui.theme.FloBizAssignmentTheme
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.colorPrimary
import com.only.flobizassignment.ui.theme.colorPrimaryVariant
import com.only.flobizassignment.ui.theme.textColorSecondary
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val isConnected = rememberSaveable { mutableStateOf(context.isNetworkAvailable()) }

    LaunchedEffect(Unit) {
        while (true) {
            isConnected.value = context.isNetworkAvailable()
            delay(1000)
        }
    }

    DisposableEffect(context) {
        context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        onDispose {
            context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val expenses by viewModel.expenses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val filteredExpenses = expenses.filter {
        it.description.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchExpenses()
    }

    BackHandler(enabled = isSearching) {
        isSearching = false
        searchQuery = ""
    }

    if (!isConnected.value) {
        Dialog(
            onDismissRequest = { isConnected },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(R.drawable.internet),
                        contentDescription = "No internet",
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Your Offline",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "please check you internet connection",
                        color = Color.Black,
                        fontSize = 10.sp,
                    )
                }
            }
        }
    } else {

        FloBizAssignmentTheme {
            Scaffold(
                topBar = {
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                            .background(Color.White),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .background(
                                    color = colorPrimaryVariant,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { isSearching = true }
                        ) {
                            Row(
                                modifier = Modifier
                                    .height(45.dp)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(end = 10.dp),
                                    painter = painterResource(R.drawable.search),
                                    contentDescription = "Image",
                                )

                                if (isSearching) {
                                    TextField(
                                        value = searchQuery,
                                        onValueChange = { searchQuery = it },
                                        label = {
                                            Text(
                                                searchQuery,
                                                color = textColorSecondary,
                                                fontSize = 14.sp,
                                            )
                                        },
                                        colors = TextFieldDefaults.colors(
                                            focusedTextColor = textColorSecondary,
                                            unfocusedTextColor = textColorSecondary,
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                colorPrimaryVariant,
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        singleLine = true
                                    )
                                } else {
                                    Text(
                                        text = "Search",
                                        color = textColorSecondary,
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                        }
                    }
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(top = 90.dp)
                            .fillMaxSize()
                            .background(background)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Recent Transactions",
                                color = textColorSecondary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Box(Modifier.fillMaxSize()) {
                                if (isLoading) {
                                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                                } else if (filteredExpenses.isEmpty()) {
                                    Text(
                                        text = "No items available",
                                        color = Color.Gray,
                                        modifier = Modifier.align(Alignment.Center),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                } else {
                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        itemsIndexed(
                                            filteredExpenses,
                                            key = { _, expense -> expense.id }
                                        ) { index, expense ->

                                            val dismissState = rememberSwipeToDismissBoxState(
                                                confirmValueChange = { dismissValue ->
                                                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                                        viewModel.deleteExpense(expense)
                                                        true
                                                    } else {
                                                        false
                                                    }
                                                }
                                            )

                                            SwipeToDismissBox(
                                                state = dismissState,
                                                backgroundContent = {
                                                    DismissBackground(
                                                        dismissState = dismissState,
                                                        height = 80.dp
                                                    )
                                                },
                                                content = {
                                                    LazyItem(expense = expense) {
                                                        val expenseJson = Gson().toJson(expense)
                                                        navController.navigate(
                                                            Routes.ExpenseDetailScreen.createRoute(
                                                                expenseJson
                                                            )
                                                        )
                                                    }
                                                },
                                                modifier = Modifier.animateItemPlacement()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            navController.navigate("record_screen")
                        },
                        icon = { Icon(Icons.Filled.Add, contentDescription = "Add New") },
                        text = { Text(text = "Add New") },
                        contentColor = Color.White,
                        containerColor = colorPrimary,
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier.width(190.dp)
                    )
                },
                floatingActionButtonPosition = FabPosition.Center
            )
        }
    }
}

@Composable
fun LazyItem(expense: Expenses, onClick: () -> Unit) {

    var expenseIndex = 1
    val groupedExpenses = expense.type == "Expense"
    val expenseType = if(groupedExpenses) {
        "#${expenseIndex++}"
    } else {
        "#${expenseIndex++}"
    }

    Card(
        onClick = {onClick()},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(5.dp)
            )
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(5.dp)
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = expense.description,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = expense.amount.toString(),
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "${expense.type} $expenseType",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = expense.date,
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState, height: Dp) {
    val alignment = when (dismissState.currentValue) {
        SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
        else -> Alignment.CenterEnd
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.White)
            .padding(20.dp),
        contentAlignment = alignment
    ) {
        Image(
            painter = painterResource(R.drawable.swipe_delete),
            contentDescription = "Delete",
        )
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


fun Context.requireActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("No activity was present but it is required.")
}

@Preview(showSystemUi = true)
@Composable
fun DashboardScreenPreview(){
}