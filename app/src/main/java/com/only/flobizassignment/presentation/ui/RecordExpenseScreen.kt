package com.only.flobizassignment.presentation.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.only.flobizassignment.R
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.colorControlNormal
import com.only.flobizassignment.ui.theme.colorSecondary
import com.only.flobizassignment.ui.theme.textColorPrimary
import com.only.flobizassignment.ui.theme.textColorSecondary
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordExpenseScreen() {

    val dateState = rememberDatePickerState()
    val millisToLocalDate = dateState.selectedDateMillis?.let {
        CustomDatePicker().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        CustomDatePicker().dateToString(millisToLocalDate)
    } ?: "${LocalDate.now()}"

    var datePickerShowing by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "back"
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Record Expense",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        content = { padding ->

            val radioOptions = listOf("Expense", "Income")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(background)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    radioOptions.forEach { option ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .background(
                                    color = if (option == selectedOption) Color.Transparent else Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (option == selectedOption) colorSecondary else colorControlNormal,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { onOptionSelected(option) }
                                .padding(vertical = 15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                RadioButton(
                                    selected = (option == selectedOption),
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = colorSecondary,
                                        unselectedColor = Color.Gray
                                    )
                                )

                                Spacer(modifier = Modifier.width(15.dp))

                                Text(
                                    text = option,
                                    fontWeight = FontWeight.Bold,
                                    color = textColorSecondary
                                )


                                Spacer(modifier = Modifier.padding(end = 30.dp))
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "DATE",
                            fontSize = 13.sp,
                            color = textColorPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.padding(top = 20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    datePickerShowing = true
                                }
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .border(
                                    width = 1.dp,
                                    color = colorControlNormal,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (datePickerShowing) {
                                DatePickerDialog(
                                    colors = DatePickerDefaults.colors(background),
                                    onDismissRequest = { datePickerShowing = false },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                datePickerShowing = false
                                                millisToLocalDate?.let {
                                                    dateToString
                                                }
                                            }
                                        ) {
                                            Text(text = "OK")
                                        }
                                    },
                                    dismissButton = {
                                        Button(
                                            onClick = { datePickerShowing = false }
                                        ) {
                                            Text(text = "Cancel")
                                        }
                                    }
                                ) {
                                    DatePicker(state = dateState, showModeToggle = true)
                                }
                            }
                            if (dateToString.isEmpty()) {
                                Text(
                                    "Date Of Birth",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
                                Text(
                                    text = dateToString,
                                    color = textColorSecondary,
                                    fontSize = 15.sp,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                            }

                            Image(
                                modifier = Modifier.padding(end = 20.dp),
                                painter = painterResource(R.drawable.calendar),
                                contentDescription = "calendar"
                            )
                        }
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
                            text = "DESCRIPTION",
                            fontSize = 13.sp,
                            color = textColorPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.padding( top = 20.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .height(50.dp)
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .border(
                                    width = 1.dp,
                                    color = colorControlNormal,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            verticalArrangement = Arrangement.Center
                        ) {
                            BasicTextField(
                                textStyle = TextStyle(
                                    color = textColorSecondary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(start = 20.dp),
                                value = "Electric Bill",
                                onValueChange = {},
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
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total Amount",
                            fontSize = 16.sp,
                            color = textColorPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "₹",
                                    fontSize = 15.sp,
                                    color = textColorSecondary,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.padding(end = 10.dp))

                                BasicTextField(
                                    textStyle = TextStyle(
                                        color = textColorSecondary,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    value = "3500",
                                    onValueChange = {},
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecordExpenseScreenPreview() {
    RecordExpenseScreen()
}