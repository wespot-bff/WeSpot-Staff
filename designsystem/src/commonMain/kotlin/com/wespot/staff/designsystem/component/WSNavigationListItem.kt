package com.wespot.staff.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wespot.staff.common.extensions.clickableSingle
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import org.jetbrains.compose.resources.painterResource
import wespotstaff.designsystem.generated.resources.Res
import wespotstaff.designsystem.generated.resources.right_arrow

@Composable
fun WSNavigationListItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickableSingle { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            text = text,
            style = StaticTypography().body4,
            color = WeSpotThemeManager.colors.txtTitleColor,
        )

        Icon(
            painter = painterResource(Res.drawable.right_arrow),
            contentDescription = "rightArrow",
            tint = WeSpotThemeManager.colors.abledIconColor,
        )
    }
}
