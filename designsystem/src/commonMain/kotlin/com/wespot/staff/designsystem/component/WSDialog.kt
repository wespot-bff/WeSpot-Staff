package com.wespot.staff.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager

@Composable
fun WSDialog(
    dialogType: WSDialogType = WSDialogType.TwoButton,
    title: String,
    subTitle: String = "",
    okButtonText: String = "",
    cancelButtonText: String = "",
    textFieldText: String = "",
    okButtonClick: () -> Unit = {},
    cancelButtonClick: () -> Unit = {},
    onTextFieldTextChanged: (String) -> Unit = {},
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .clip(WeSpotThemeManager.shapes.medium)
                .background(WeSpotThemeManager.colors.modalColor)
                .padding(top = 32.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            WSDialogContent(title = title, subTitle = subTitle)

            when (dialogType) {
                is WSDialogType.TwoButton -> {
                    WSDialogTwoButton(
                        okButtonText = okButtonText,
                        cancelButtonText = cancelButtonText,
                        okButtonClick = okButtonClick,
                        cancelButtonClick = cancelButtonClick,
                    )
                }

                is WSDialogType.OneButton -> {
                    WSButton(
                        text = okButtonText,
                        onClick = okButtonClick,
                        buttonType = WSButtonType.Primary,
                        paddingValues = PaddingValues(0.dp),
                        content = { it() },
                    )
                }

                is WSDialogType.TextField -> {
                    WSTextField(
                        value = textFieldText,
                        onValueChange = onTextFieldTextChanged,
                        textFieldType = WsTextFieldType.Normal,
                        placeholder = "",
                    )

                    WSButton(
                        text = okButtonText,
                        onClick = okButtonClick,
                        buttonType = WSButtonType.Primary,
                        paddingValues = PaddingValues(0.dp),
                        content = { it() },
                    )
                }
            }
        }
    }
}

@Composable
private fun WSDialogContent(
    title: String,
    subTitle: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = StaticTypography().header1,
            color = WeSpotThemeManager.colors.txtTitleColor,
        )

        if (subTitle.isNotEmpty()) {
            Text(
                text = subTitle,
                style = StaticTypography().body6,
                color = WeSpotThemeManager.colors.txtSubColor,
            )
        }
    }
}

@Composable
private fun WSDialogTwoButton(
    okButtonText: String,
    cancelButtonText: String,
    okButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(modifier = Modifier.weight(1f)) {
            WSButton(
                onClick = cancelButtonClick,
                buttonType = WSButtonType.Secondary,
                text = cancelButtonText,
                paddingValues = PaddingValues(0.dp),
            ) {
                it()
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            WSButton(
                onClick = okButtonClick,
                buttonType = WSButtonType.Primary,
                text = okButtonText,
                paddingValues = PaddingValues(0.dp),
            ) {
                it()
            }
        }
    }
}

sealed interface WSDialogType {
    data object TwoButton : WSDialogType
    data object OneButton : WSDialogType
    data object TextField : WSDialogType
}
