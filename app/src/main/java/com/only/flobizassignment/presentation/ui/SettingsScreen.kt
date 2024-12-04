package com.only.flobizassignment.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.only.flobizassignment.R
import com.only.flobizassignment.ui.theme.background
import com.only.flobizassignment.ui.theme.textColorPrimary

@Composable
fun SettingsScreen() {
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
                    .padding(10.dp),
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

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}