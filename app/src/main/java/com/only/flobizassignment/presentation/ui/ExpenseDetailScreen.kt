package com.only.flobizassignment.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.only.flobizassignment.R
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.colorSecondary
import com.only.flobizassignment.ui.theme.textColorSecondary
import com.only.flobizassignment.ui.theme.textColorTertiary

@Composable
fun ExpenseDetailScreen() {

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 50.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.White)
                        .padding(20.dp),
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

                    Row(
                        modifier = Modifier.padding(end = 10.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "back"
                        )

                        Spacer(modifier = Modifier.width(25.dp))

                        Image(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.delete),
                            contentDescription = "back"
                        )
                    }
                }
            }
        },
        content = { padding->
            Column(
                modifier = Modifier.padding(padding)
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
                            text = "Expense #1",
                            fontSize = 15.sp,
                            color = colorSecondary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.padding( top = 15.dp))

                        Text(
                            text = "02 Dec 2020",
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

                        Spacer(modifier = Modifier.padding( top = 20.dp))

                        Text(
                            text = "Electric Bill",
                            fontSize = 18.sp,
                            color = textColorTertiary,
                            fontWeight = FontWeight.Bold
                        )
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

                        Text(
                            text = "100000",
                            fontSize = 18.sp,
                            color = textColorSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun ExpenseDetailScreenPreview() {
    ExpenseDetailScreen()
}