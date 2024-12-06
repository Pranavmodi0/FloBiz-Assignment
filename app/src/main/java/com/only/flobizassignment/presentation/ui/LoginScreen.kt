package com.only.flobizassignment.presentation.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import com.only.flobizassignment.R
import com.only.flobizassignment.ui.theme.colorSecondary

@Composable
fun LoginScreen(
    onClick: () -> Unit,
    loading: Boolean = false,
){

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

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
                    .fillMaxSize()
                    .background(White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Welcome to FloBiz",
                    fontSize = 27.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Black, colorSecondary)
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
                        onClick()
                    }
                )

                if (loading){
                    Dialog(
                        onDismissRequest = { !loading },
                        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(90.dp)
                                .background(
                                    White,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        ) {

                            CircularProgressIndicator(
                                modifier = Modifier.height(18.dp).width(18.dp),
                                strokeWidth = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                            )

                            Spacer(modifier = Modifier.height(19.dp))

                            Text(
                                text = "Please wait..",
                                color = Black,
                                modifier = Modifier
                                    .padding(bottom = 18.dp)
                                    .align(Alignment.BottomCenter),
                            )
                        }
                    }
                }
            }
        }
    )
}

