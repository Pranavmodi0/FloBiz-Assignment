package com.only.flobizassignment.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
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

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val view = LocalView.current
    val window = (view.context as Activity).window
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true

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
                                    placeholder = {
                                        Text("Search", color = textColorSecondary)
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
                                        )
                                        .padding(horizontal = 8.dp),
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
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(filteredExpenses) { expense ->
                                        LazyItem(expense = expense) {
                                            val expenseJson = Gson().toJson(expense)
                                            navController.navigate(
                                                Routes.ExpenseDetailScreen.createRoute(expenseJson)
                                            )
                                        }
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

@Composable
fun LazyItem(expense: Expenses, onClick: () -> Unit) {
    Card(
        onClick = {onClick()},
        modifier = Modifier
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
                    text = expense.type,
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