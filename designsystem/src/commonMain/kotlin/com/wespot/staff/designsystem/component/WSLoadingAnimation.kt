package com.wespot.staff.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wespot.staff.designsystem.theme.WeSpotThemeManager

@Composable
fun WSLoadingAnimation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = false) { },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = WeSpotThemeManager.colors.primaryColor
        )
    }
}
