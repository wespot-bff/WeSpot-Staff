package com.wespot.staff.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WSHomeTabRow(
    selectedTabIndex: Int,
    tabList: ImmutableList<String>,
    onTabSelected: (Int) -> Unit,
) {
    val paddingModifier = when (selectedTabIndex) {
        0 -> Modifier.padding(start = 20.dp)
        1 -> Modifier.padding(end = 20.dp)
        else -> Modifier
    }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = WeSpotThemeManager.colors.backgroundColor,
        contentColor = WeSpotThemeManager.colors.disableIcnColor,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .then(paddingModifier),
                color = WeSpotThemeManager.colors.abledTxtColor,
            )
        },
        divider = {
            HorizontalDivider(color = WeSpotThemeManager.colors.tertiaryBtnColor)
        },
    ) {
        tabList.forEachIndexed { index, tab ->
            val selected = selectedTabIndex == index
            CompositionLocalProvider(LocalRippleConfiguration provides MyRippleConfiguration) {
                Tab(
                    selected = selected,
                    onClick = { onTabSelected(index) },
                    modifier = Modifier
                        .padding(vertical = 11.dp),
                ) {
                    Text(
                        text = tab,
                        style = StaticTypography().body3,
                        color = if (selected) {
                            WeSpotThemeManager.colors.abledTxtColor
                        } else {
                            WeSpotThemeManager.colors.disableIcnColor
                        },
                        modifier = Modifier.padding(
                            if (index == 0) {
                                PaddingValues(start = 20.dp)
                            } else {
                                PaddingValues(end = 20.dp)
                            },
                        ),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
internal val MyRippleConfiguration =
    RippleConfiguration(
        color = Color.Unspecified,
        rippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
    )
