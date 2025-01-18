package com.wespot.staff

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.navigation.BottomBarState
import com.wespot.staff.navigation.RootConfiguration
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavigationTab(
    selectedNavigation: RootConfiguration,
    onNavigationSelected: (RootConfiguration) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        containerColor = WeSpotThemeManager.colors.naviColor,
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomBarState.entries.forEach { state ->
                TabItem(
                    icon = painterResource(state.icon),
                    emptyIcon = painterResource(state.emptyIcon),
                    title = stringResource(state.title),
                    description = stringResource(state.title),
                    selected = selectedNavigation == state.config,
                    onClick = { onNavigationSelected(state.config) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.TabItem(
    icon: Painter,
    emptyIcon: Painter,
    title: String,
    description: String,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .weight(1f)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = if (selected) {
                    icon
                } else {
                    emptyIcon
                },
                contentDescription = description,
            )

            Text(
                text = title,
                style = StaticTypography().body9,
                color = if (selected) {
                    WeSpotThemeManager.colors.abledIconColor
                } else {
                    WeSpotThemeManager.colors.disableIcnColor
                },
            )
        }
    }
}
