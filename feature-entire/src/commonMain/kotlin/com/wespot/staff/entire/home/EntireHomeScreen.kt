package com.wespot.staff.entire.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wespot.staff.designsystem.component.WSNavigationListItem
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.designsystem.util.LocalSnackbarHostState

@Composable
fun EntireHomeScreen(component: EntireHomeComponent) {
    val snackbarHostState = LocalSnackbarHostState.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 32.dp),
                text = "전체 관리",
                style = StaticTypography().header1,
                color = WeSpotThemeManager.colors.txtTitleColor,
            )

            WSNavigationListItem(
                text = "컨피그 값 관리",
                onClick = component::navigateToConfigurationScreen
            )

            WSNavigationListItem(
                text = "컨피그 값 등록",
                onClick = component::navigateToAddConfigurationScreen
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = WeSpotThemeManager.colors.cardBackgroundColor,
            )

            WSNavigationListItem(
                text = "알림 생성",
                onClick = component::navigateToNotificationScreen
            )
        }

        LaunchedEffect(Unit) {
            component.toastMessage?.let {
                snackbarHostState.showSnackbar(it)
            }
        }
    }
}
