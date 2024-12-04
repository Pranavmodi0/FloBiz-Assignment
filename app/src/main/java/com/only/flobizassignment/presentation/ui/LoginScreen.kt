package com.only.flobizassignment.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.only.flobizassignment.R
import com.only.flobizassignment.ui.theme.colorSecondary

@Composable
fun LoginScreen(){
    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome to FloBiz",
                    fontSize = 27.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color.Black, colorSecondary)
                        )
                    )
                )

                Spacer(modifier = Modifier.padding(bottom = 50.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.padding(top = 100.dp))

                Image(
                    painter = painterResource(id = R.drawable.google_login),
                    contentDescription = null
                )
            }
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun LoginPreview(){
    LoginScreen()
}