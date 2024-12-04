package com.only.flobizassignment.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.only.flobizassignment.R
import com.only.flobizassignment.ui.theme.FloBizAssignmentTheme
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.colorPrimary
import com.only.flobizassignment.ui.theme.colorPrimaryVariant
import com.only.flobizassignment.ui.theme.textColorSecondary

@Composable
fun DashboardScreen() {

    FloBizAssignmentTheme {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
                            .background(
                                color = colorPrimaryVariant,
                                shape = RoundedCornerShape(10.dp)
                            )
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

                            Text(
                                text = "Search",
                                color = textColorSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(top = 60.dp)
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

                        LazyColumn {
                            items(10) {
                                LazyItem()
                            }
                        }
                    }
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { },
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
fun LazyItem(){
    Card(
        onClick = {},
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
                        text = "Expense title",
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "1000",
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Expense no",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Date",
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

@Preview(showSystemUi = true)
@Composable
fun DashboardScreenPreview(){
    DashboardScreen()
}