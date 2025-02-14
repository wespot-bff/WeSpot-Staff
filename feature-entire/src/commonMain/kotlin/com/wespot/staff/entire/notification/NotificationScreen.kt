package com.wespot.staff.entire.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wespot.staff.common.extensions.clickableSingle
import com.wespot.staff.common.extensions.collectSideEffect
import com.wespot.staff.designsystem.component.WSButton
import com.wespot.staff.designsystem.component.WSLoadingAnimation
import com.wespot.staff.designsystem.component.WSTextField
import com.wespot.staff.designsystem.component.WSTopBar
import com.wespot.staff.designsystem.component.WsTextFieldType
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.designsystem.util.snackbar.LocalSnackbarHost
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    component: NotificationComponent,
    viewModel: NotificationViewModel = koinViewModel()
) {
    val snackbarHost = LocalSnackbarHost.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            NotificationSideEffect.NavigateToHome -> {
                component.navigateToHomeScreen()
            }

            is NotificationSideEffect.ShowSnackbar -> {
                snackbarHost.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        topBar = {
            WSTopBar(
                title = "",
                canNavigateBack = true,
                navigateUp = component::navigateUp,
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .clickableSingle {
                keyboardController?.hide()
            },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "알림 생성",
                    style = StaticTypography().header1,
                    color = WeSpotThemeManager.colors.txtTitleColor,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "푸시 알림에 들어갈 제목과 내용을 작성해주세요.",
                    style = StaticTypography().body6,
                    color = WeSpotThemeManager.colors.txtSubColor,
                )

                Spacer(modifier = Modifier.height(32.dp))

                EditTextField(
                    title = "알림 제목",
                    value = state.title,
                    placeHolder = "알림 제목을 입력하세요",
                    onValueChange = viewModel::setTitle,
                )

                Spacer(modifier = Modifier.height(16.dp))

                EditTextField(
                    title = "알림 내용",
                    value = state.body,
                    placeHolder = "알림 내용을 입력하세요",
                    isBody = true,
                    onValueChange = viewModel::setBody,
                )
            }

            WSButton(
                text = "알림 생성하기",
                onClick = { viewModel.publishNotification() },
                content = { it() },
            )
        }
    }

    if (state.isLoading) {
        WSLoadingAnimation()
    }
}

@Composable
private fun EditTextField(
    title: String,
    value: String,
    placeHolder: String,
    isBody: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    /** Firebase Notification Message 사이즈 1000자 미만 */
    Column {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = title,
            style = StaticTypography().body4,
        )

        WSTextField(
            value = value,
            placeholder = placeHolder,
            onValueChange = onValueChange,
            textFieldType = if (isBody) WsTextFieldType.Message else WsTextFieldType.Normal
        )
    }
}
