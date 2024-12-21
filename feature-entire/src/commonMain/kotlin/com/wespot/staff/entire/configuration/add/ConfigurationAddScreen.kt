package com.wespot.staff.entire.configuration.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationAddScreen(
    component: ConfigurationAddComponent,
    viewModel: ConfigurationAddViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            ConfigurationAddSideEffect.NavigateToHomeScreen -> {
                component.navigateToHomeScreen("등록 완료")
            }

            is ConfigurationAddSideEffect.ShowErrorToast -> {
                snackbarHostState.showSnackbar(it.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                    text = "컨피그 값 생성",
                    style = StaticTypography().header1,
                    color = WeSpotThemeManager.colors.txtTitleColor,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Key는 공백 없이 대문자와 언더바로 구성해주세요.",
                    style = StaticTypography().body6,
                    color = WeSpotThemeManager.colors.txtSubColor,
                )

                Spacer(modifier = Modifier.height(32.dp))

                EditTextField(
                    title = "컨피그 값 Key",
                    value = state.remoteConfigKey,
                    placeHolder = "컨피그 값 Key를 입력하세요",
                    onValueChange = viewModel::setRemoteConfigKey,
                )

                Spacer(modifier = Modifier.height(16.dp))

                EditTextField(
                    title = "컨피그 값 Value",
                    value = state.remoteConfigValue,
                    placeHolder = "컨피그 값 Value를 입력하세요",
                    isBody = true,
                    onValueChange = viewModel::setRemoteConfigValue,
                )
            }

            WSButton(
                text = "컨피그 값 등록하기",
                onClick = viewModel::postRemoteConfig,
                content = { it() },
                enabled = state.remoteConfigKey.isNotBlank() && state.remoteConfigValue.isNotBlank()
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
