package com.only.flobizassignment.presentation.ui

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.only.flobizassignment.R
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.textColorPrimary

@Composable
fun SettingsScreen(
    navController: NavController
) {

    val context = LocalContext.current

    DisposableEffect(context) {
        context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        onDispose {
            context.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(background)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("login_screen"){
                        popUpTo(0) { inclusive = true }
                            }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logout),
                    contentDescription = "Logout",
                    modifier = Modifier.padding(end = 10.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Logout",
                    color = textColorPrimary,
                    fontSize = 12.sp,
                )
            }
        }
    }
}
