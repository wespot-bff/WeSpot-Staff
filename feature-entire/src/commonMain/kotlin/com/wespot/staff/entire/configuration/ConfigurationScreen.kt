package com.wespot.staff.entire.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wespot.staff.common.extensions.collectSideEffect
import com.wespot.staff.designsystem.component.BottomSheetText
import com.wespot.staff.designsystem.component.WSBottomSheet
import com.wespot.staff.designsystem.component.WSDialog
import com.wespot.staff.designsystem.component.WSDialogType
import com.wespot.staff.designsystem.component.WSListItem
import com.wespot.staff.designsystem.component.WSLoadingAnimation
import com.wespot.staff.designsystem.component.WSTopBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(
    component: ConfigurationComponent,
    viewModel: ConfigurationViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showEditDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is ConfigurationSideEffect.ShowEditConfigurationDialog -> {
                showEditDialog = true
            }

            is ConfigurationSideEffect.ShowErrorToast -> {
                snackbarHostState.showSnackbar(it.message)
            }

            is ConfigurationSideEffect.ShowToast -> {
                snackbarHostState.showSnackbar(it.message)
            }

            ConfigurationSideEffect.ShowBottomSheet -> {
                showBottomSheet = true
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            WSTopBar(
                title = "컨피그 값 관리",
                canNavigateBack = true,
                navigateUp = component::navigateUp,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = state.remoteConfigList,
                 key = { item -> item.key },
            ) { item ->
                /** Description이 비어있으면 key로만 구성한다. */
                WSListItem(
                    title = item.key,
                    subTitle = if (item.description.isNotBlank()) {
                        "${item.description}\n${item.value}"
                    } else item.value,
                    selected = false,
                    isMultiLine = true,
                    onClick = {
                        viewModel.selectRemoteConfig(item)
                    },
                )
            }
        }
    }

    if (state.isLoading) {
        WSLoadingAnimation()
    }

    if (showBottomSheet) {
        WSBottomSheet(
            closeSheet = { showBottomSheet = false },
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                BottomSheetText(
                    text = "삭제하기",
                    onClick = {
                        viewModel.handleDeleteBottomSheetClicked()
                        showBottomSheet = false
                    },
                )

                BottomSheetText(
                    text = "수정하기",
                    onClick = {
                        viewModel.handleEditBottomSheetClicked()
                        showBottomSheet = false
                    },
                    showDivider = false,
                )
            }
        }
    }

    if (showEditDialog) {
        WSDialog(
            dialogType = WSDialogType.TextField,
            title = state.selectedRemoteConfig.key,
            subTitle = state.selectedRemoteConfig.description,
            textFieldText = state.selectedRemoteConfig.value,
            okButtonText = "수정하기",
            okButtonClick = {
                viewModel.editRemoteConfig()
                showEditDialog = false
            },
            onTextFieldTextChanged = viewModel::setRemoteConfigValue,
            onDismissRequest = {
                showEditDialog = false
            },
        )
    }
}
