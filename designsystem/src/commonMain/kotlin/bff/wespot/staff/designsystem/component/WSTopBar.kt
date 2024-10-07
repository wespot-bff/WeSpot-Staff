package bff.wespot.staff.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import bff.wespot.staff.designsystem.theme.Gray400
import bff.wespot.staff.designsystem.theme.StaticTypography
import bff.wespot.staff.designsystem.theme.WeSpotThemeManager
import org.jetbrains.compose.resources.painterResource
import wespotstaff.designsystem.generated.resources.Res
import wespotstaff.designsystem.generated.resources.left_arrow
import wespotstaff.designsystem.generated.resources.search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WSTopBar(
    title: String,
    navigation: @Composable () -> Unit = {},
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    action: @Composable RowScope.(textStyle: TextStyle) -> Unit = { },
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = StaticTypography().header2,
                color = WeSpotThemeManager.colors.txtTitleColor,
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    modifier = Modifier.padding(start = 4.dp),
                    onClick = { navigateUp.invoke() },
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.left_arrow),
                        contentDescription = "back_icon",
                    )
                }
            } else {
                navigation()
            }
        },
        actions = {
            action(StaticTypography().body4)
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WeSpotThemeManager.colors.backgroundColor,
            navigationIconContentColor = Gray400,
            actionIconContentColor = Gray400,
        ),
    )
}
