package com.only.flobizassignment.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.only.flobizassignment.R
import com.only.flobizassignment.presentation.model.LoginState
import com.only.flobizassignment.presentation.model.LoginViewModel
import com.only.flobizassignment.presentation.ui.navigation.Routes
import com.only.flobizassignment.ui.theme.colorSecondary

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
){

    val loginState by viewModel.loginState.collectAsState()

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
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        viewModel.signInWithGoogle()
                    }
                )

                when (loginState) {
                    is LoginState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is LoginState.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(Routes.DashboardScreen.routes)
                        }
                    }
                    is LoginState.Error -> {
                        Text(
                            text = (loginState as LoginState.Error).message,
                            color = Color.Red
                        )
                    }
                    else -> {}
                }
            }
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun LoginPreview(){
    LoginScreen(
        navController = NavController(LocalContext.current)
    )
}