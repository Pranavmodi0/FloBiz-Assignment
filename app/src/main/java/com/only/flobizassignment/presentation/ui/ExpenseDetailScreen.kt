package com.only.flobizassignment.presentation.ui

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.only.flobizassignment.R
import com.only.flobizassignment.data.Expenses
import com.only.flobizassignment.presentation.model.ExpenseDetailViewModel
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.colorPrimaryVariant
import com.only.flobizassignment.ui.theme.colorSecondary
import com.only.flobizassignment.ui.theme.textColorPrimary
import com.only.flobizassignment.ui.theme.textColorSecondary

@Composable
fun ExpenseDetailScreen(
    expenses: Expenses,
    navController: NavController,
    authRepository: FirebaseAuth,
    viewModel: ExpenseDetailViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    DisposableEffect(context) {
        context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        onDispose {
            context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    var isEditMode by remember { mutableStateOf(false) }
    val type by remember { mutableStateOf(expenses.type) }
    val date by remember { mutableStateOf(expenses.date) }
    var description by remember { mutableStateOf(expenses.description) }
    var amount by remember { mutableStateOf(expenses.amount.toString()) }
    val isLoading by viewModel.isLoading.collectAsState()
    val userId = authRepository.currentUser?.uid.toString()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.White)
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(30.dp)
                            .clickable {
                                navController.popBackStack()
                            },
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "back"
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Record Expense",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColorPrimary
                    )

                    Row(
                        modifier = Modifier.padding(end = 10.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (isEditMode) {
                            Image(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        viewModel.updateExpense(
                                            expenseId = expenses.id,
                                            updatedExpense = Expenses(
                                                id = expenses.id,
                                                type = type,
                                                description = description,
                                                amount = amount.toDouble(),
                                                uid = userId,
                                                date = date
                                            )
                                        ) {
                                            isEditMode = false
                                        }
                                    },
                                painter = painterResource(R.drawable.check),
                                contentDescription = "save"
                            )
                        } else {
                            Image(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { isEditMode = true },
                                painter = painterResource(R.drawable.edit),
                                contentDescription = "edit"
                            )
                        }

                        Spacer(modifier = Modifier.width(25.dp))

                        Image(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    viewModel.deleteExpense(expenses.id) {
                                        navController.popBackStack()
                                    }
                                },
                            painter = painterResource(R.drawable.delete),
                            contentDescription = "delete"
                        )
                    }
                }
            }
        },
        content = { padding->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier.padding(padding)
                        .fillMaxSize()
                        .background(background)
                ) {

                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = type,
                                fontSize = 15.sp,
                                color = colorSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.padding(top = 15.dp))

                            Text(
                                text = date,
                                fontSize = 15.sp,
                                color = textColorSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Description",
                                fontSize = 16.sp,
                                color = textColorSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            if (isEditMode) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = colorSecondary,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(4.dp)
                                ) {
                                    TextField(
                                        value = description,
                                        onValueChange = { description = it },
                                        label = { Text("Description") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                width = 1.dp,
                                                color = colorSecondary,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clip(RoundedCornerShape(10.dp)),
                                        colors = TextFieldDefaults.colors().copy(
                                            focusedContainerColor = colorPrimaryVariant,
                                            focusedTextColor = textColorSecondary,
                                            unfocusedTextColor = textColorSecondary,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedContainerColor = colorPrimaryVariant,
                                            unfocusedIndicatorColor = Color.Transparent
                                        )
                                    )
                                }
                            } else {
                                Text(
                                    text = description,
                                    fontSize = 15.sp,
                                    color = textColorSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Amount",
                                fontSize = 18.sp,
                                color = textColorSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            if (isEditMode) {
                                TextField(
                                    value = amount,
                                    onValueChange = { amount = it },
                                    label = { Text("Amount") },
                                    modifier = Modifier.width(100.dp).background(
                                        Color.White,
                                        shape = RoundedCornerShape(10.dp))
                                        .border(
                                            width = 1.dp,
                                            color = colorSecondary,
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    colors = TextFieldDefaults.colors().copy(
                                        focusedContainerColor = colorPrimaryVariant,
                                        focusedTextColor = textColorSecondary,
                                        unfocusedTextColor = textColorSecondary,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedContainerColor = colorPrimaryVariant,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )
                            } else {
                                Text(
                                    text = amount,
                                    fontSize = 18.sp,
                                    color = textColorSecondary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}