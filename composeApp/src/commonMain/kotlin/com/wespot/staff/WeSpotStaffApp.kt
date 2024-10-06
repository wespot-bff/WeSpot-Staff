package com.wespot.staff

import androidx.compose.runtime.*
import bff.wespot.staff.designsystem.theme.WeSpotTheme
import com.wespot.staff.home.HomeScreen

@Composable
fun WeSpotStaffApp() {
    WeSpotTheme {
        HomeScreen()
    }
}
