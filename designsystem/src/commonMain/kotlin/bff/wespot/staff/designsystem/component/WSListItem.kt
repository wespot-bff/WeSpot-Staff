package bff.wespot.staff.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import bff.wespot.staff.designsystem.theme.StaticTypography
import bff.wespot.staff.designsystem.theme.WeSpotThemeManager

@Composable
fun WSListItem(
    title: String,
    subTitle: String,
    selected: Boolean,
    onClick: () -> Unit = { },
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp)
            .clip(WeSpotThemeManager.shapes.medium)
            .border(
                width = 1.dp,
                color = if (selected) {
                    WeSpotThemeManager.colors.primaryColor
                } else {
                    WeSpotThemeManager.colors.cardBackgroundColor
                },
                shape = WeSpotThemeManager.shapes.medium,
            )
            .background(WeSpotThemeManager.colors.cardBackgroundColor)
            .clickable { onClick.invoke() },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = title,
                style = StaticTypography().body2,
                color = WeSpotThemeManager.colors.txtTitleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = subTitle,
                style = StaticTypography().body5,
                color = WeSpotThemeManager.colors.txtSubColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
