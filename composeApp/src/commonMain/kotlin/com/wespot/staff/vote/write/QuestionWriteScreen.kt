package com.wespot.staff.vote.write

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.wespot.staff.common.clickableSingle
import com.wespot.staff.designsystem.component.WSHomeTabRow
import com.wespot.staff.designsystem.component.WSTopBar
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.vote.QuestionWriteComponent
import com.wespot.staff.vote.write.add.QuestionAddScreen
import com.wespot.staff.vote.write.add.QuestionAddViewModel
import com.wespot.staff.vote.write.parse.QuestionParseScreen
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import wespotstaff.composeapp.generated.resources.Res
import wespotstaff.composeapp.generated.resources.tab_add
import wespotstaff.composeapp.generated.resources.tab_parse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionWriteScreen(
    component: QuestionWriteComponent,
    addViewModel: QuestionAddViewModel = koinViewModel(),
) {
    val tabList = persistentListOf(
        stringResource(Res.string.tab_add),
        stringResource(Res.string.tab_parse),
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            WSTopBar(
                title = "여러 질문 작성",
                canNavigateBack = true,
                navigateUp = component::navigateUp,
            )
        },
        modifier = Modifier.clickableSingle {
            keyboardController?.hide()
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            WSHomeTabRow(
                selectedTabIndex = selectedTabIndex,
                tabList = tabList,
                onTabSelected = { index -> selectedTabIndex = index },
            )

            Crossfade(
                targetState = selectedTabIndex,
                label = "Crossfade Component ",
            ) { page ->
                when (page) {
                    QUESTION_ADD_SCREEN_INDEX -> {
                        QuestionAddScreen(
                            viewModel = addViewModel,
                            titleContent = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 32.dp),
                                    text = "질문 목록 추가하기",
                                    style = StaticTypography().header1,
                                    color = WeSpotThemeManager.colors.txtTitleColor,
                                )
                            },
                            showToast = { snackbarHostState.showSnackbar(it) },
                            navigateToQuestionScreen = component::navigateToQuestionScreen,
                        )
                    }

                    QUESTION_PARSE_SCREEN_INDEX -> {
                        QuestionParseScreen(
                            navigateToConfirmScreen = component::navigateToQuestionConfirmScreen,
                        )
                    }
                }
            }
        }
    }
}

const val QUESTION_ADD_SCREEN_INDEX = 0
const val QUESTION_PARSE_SCREEN_INDEX = 1
