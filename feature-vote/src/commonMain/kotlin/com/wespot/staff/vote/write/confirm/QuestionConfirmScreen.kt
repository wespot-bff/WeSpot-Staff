package com.wespot.staff.vote.write.confirm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.wespot.staff.common.extensions.clickableSingle
import com.wespot.staff.designsystem.component.WSTopBar
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.vote.write.add.QuestionAddScreen
import com.wespot.staff.vote.write.add.QuestionAddViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionConfirmScreen(
    component: QuestionConfirmComponent,
    questionAddViewModel: QuestionAddViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            WSTopBar(
                title = "",
                canNavigateBack = true,
                navigateUp = component::navigateUp,
            )
        },
        modifier = Modifier.clickableSingle {
            keyboardController?.hide()
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            QuestionAddScreen(
                viewModel = questionAddViewModel,
                questionList = component.questionList,
                titleContent = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        text = "변환된 질문 확인하기",
                        style = StaticTypography().header1,
                        color = WeSpotThemeManager.colors.txtTitleColor,
                    )
                },
                showToast = { snackbarHostState.showSnackbar(it) },
                navigateToQuestionScreen = component::navigateToQuestionScreen
            )
        }
    }
}
