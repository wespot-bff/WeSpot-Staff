package bff.wespot.staff.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bff.wespot.staff.designsystem.theme.StaticTypography
import bff.wespot.staff.designsystem.theme.WeSpotThemeManager

data class HeightRange(val minHeight: Dp, val maxHeight: Dp)

@Composable
fun WSButton(
    onClick: () -> Unit,
    text: String = "",
    buttonType: WSButtonType = WSButtonType.Primary,
    paddingValues: PaddingValues = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
    heightRange: HeightRange? = null,
    enabled: Boolean = true,
    borderStroke: BorderStroke? = null,
    background: Color? = null,
    pressedBorderStroke: BorderStroke? = null,
    content: @Composable RowScope.(text: @Composable () -> Unit) -> Unit,
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(paddingValues),
    ) {
        Button(
            modifier = if (heightRange == null) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
                    .fillMaxWidth()
                    .heightIn(heightRange.minHeight, heightRange.maxHeight)
            },
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = buttonType.textColor(),
                containerColor = if (isPressed) {
                    buttonType.pressColor()
                } else {
                    background ?: buttonType.background()
                },
                disabledContentColor = WeSpotThemeManager.colors.disableBtnTxtColor.copy(alpha = 0.8f),
                disabledContainerColor = WeSpotThemeManager.colors.disableBtnColor,
            ),
            interactionSource = interactionSource,
            shape = WeSpotThemeManager.shapes.small,
            enabled = enabled,
            border = if (isPressed) {
                pressedBorderStroke
            } else {
                borderStroke
            },
            contentPadding = PaddingValues(0.dp),
        ) {
            content {
                Text(
                    text = text,
                    style = StaticTypography().body3,
                    modifier = Modifier.padding(vertical = 14.dp),
                )
            }
        }
    }
}

sealed interface WSButtonType {
    @Composable
    fun background(): Color

    @Composable
    fun pressColor(): Color

    @Composable
    fun textColor(): Color

    data object Primary : WSButtonType {
        @Composable
        override fun background() = WeSpotThemeManager.colors.primaryBtnColor

        @Composable
        override fun pressColor() = Color(0xFFC0C66B)

        @Composable
        override fun textColor() = WeSpotThemeManager.colors.backgroundColor
    }

    data object Secondary : WSButtonType {
        @Composable
        override fun background() = WeSpotThemeManager.colors.secondaryBtnColor

        @Composable
        override fun pressColor() = Color(0xFF48494C)

        @Composable
        override fun textColor() = WeSpotThemeManager.colors.abledTxtColor
    }

    data object Tertiary : WSButtonType {
        @Composable
        override fun background() = WeSpotThemeManager.colors.tertiaryBtnColor

        @Composable
        override fun pressColor() = Color(0xFF27282B)

        @Composable
        override fun textColor() = WeSpotThemeManager.colors.abledTxtColor
    }
}
