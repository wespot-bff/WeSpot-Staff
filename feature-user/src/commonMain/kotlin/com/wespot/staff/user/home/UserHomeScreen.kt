package com.wespot.staff.user.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wespot.staff.common.extensions.collectSideEffect
import com.wespot.staff.designsystem.component.BottomSheetText
import com.wespot.staff.designsystem.component.WSBottomSheet
import com.wespot.staff.designsystem.component.WSLoadingAnimation
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.designsystem.util.snackbar.LocalSnackbarHost
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import wespotstaff.feature_user.generated.resources.Res
import wespotstaff.feature_user.generated.resources.user_delete
import wespotstaff.feature_user.generated.resources.user_delete_failed
import wespotstaff.feature_user.generated.resources.user_delete_success
import wespotstaff.feature_user.generated.resources.user_info_class
import wespotstaff.feature_user.generated.resources.user_info_grade
import wespotstaff.feature_user.generated.resources.user_info_school
import wespotstaff.feature_user.generated.resources.user_list_load_failed
import wespotstaff.feature_user.generated.resources.user_management_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
    component: UserHomeComponent,
    viewModel: UserHomeViewModel = koinViewModel(),
) {
    val snackbarHost = LocalSnackbarHost.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val userListLoadFailedMessage = stringResource(Res.string.user_list_load_failed)
    val userDeleteSuccessMessage = stringResource(Res.string.user_delete_success)
    val userDeleteFailedMessage = stringResource(Res.string.user_delete_failed)

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            is UserHomeSideEffect.UserListLoadFailed -> {
                snackbarHost.showSnackbar(message = userListLoadFailedMessage)
            }
            is UserHomeSideEffect.UserDeleteSuccess -> {
                snackbarHost.showSnackbar(message = userDeleteSuccessMessage)
            }
            is UserHomeSideEffect.UserDeleteFailed -> {
                snackbarHost.showSnackbar(message = userDeleteFailedMessage)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
        ) {
            Text(
                modifier = Modifier.padding(vertical = 32.dp),
                text = stringResource(Res.string.user_management_title),
                style = StaticTypography().header1,
                color = WeSpotThemeManager.colors.txtTitleColor,
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier.weight(1f),
            ) {
                items(
                    items = state.users,
                    key = { it.id }
                ) { user ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onUserClicked(user) }
                            .padding(vertical = 12.dp),
                    ) {
                        Text(
                            text = "[${user.schoolName}] ${user.name}",
                            style = StaticTypography().body4,
                            color = WeSpotThemeManager.colors.txtTitleColor,
                        )
                        Text(
                            text = user.email,
                            style = StaticTypography().body6,
                            color = WeSpotThemeManager.colors.txtSubColor,
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = WeSpotThemeManager.colors.cardBackgroundColor,
                    )
                }
            }
        }
    }

    if (state.showBottomSheet && state.selectedUser != null) {
        val user = state.selectedUser!!

        WSBottomSheet(
            closeSheet = viewModel::onDismissBottomSheet,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = user.name,
                    style = StaticTypography().header2,
                    color = WeSpotThemeManager.colors.txtTitleColor,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = user.email,
                    style = StaticTypography().body6,
                    color = WeSpotThemeManager.colors.txtSubColor,
                )
                Spacer(modifier = Modifier.height(16.dp))

                UserInfoRow(stringResource(Res.string.user_info_school), user.schoolName)
                UserInfoRow(stringResource(Res.string.user_info_grade), "${user.grade}")
                UserInfoRow(stringResource(Res.string.user_info_class), "${user.classNumber}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = WeSpotThemeManager.colors.cardBackgroundColor,
            )

            BottomSheetText(
                text = stringResource(Res.string.user_delete),
                showDivider = false,
                onClick = viewModel::deleteUser,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (state.isLoading) {
        WSLoadingAnimation()
    }
}

@Composable
private fun UserInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = value,
            style = StaticTypography().body4,
            color = WeSpotThemeManager.colors.txtTitleColor,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = StaticTypography().body6,
            color = WeSpotThemeManager.colors.txtSubColor,
        )
    }
}
