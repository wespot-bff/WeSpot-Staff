package com.wespot.staff.vote.question

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wespot.staff.common.extensions.clickableSingle
import com.wespot.staff.common.extensions.collectEvent
import com.wespot.staff.designsystem.component.WSButton
import com.wespot.staff.designsystem.component.WSListItem
import com.wespot.staff.designsystem.component.WSLoadingAnimation
import com.wespot.staff.designsystem.component.WSTextField
import com.wespot.staff.designsystem.component.WSTopBar
import com.wespot.staff.designsystem.component.WsTextFieldType
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import wespotstaff.feature_vote.generated.resources.Res
import wespotstaff.feature_vote.generated.resources.search

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    component: QuestionComponent,
    viewModel: QuestionViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.uiEvent.collectEvent {
        when (it) {
            is QuestionUiEvent.QuestionPostEvent -> {
                snackbarHostState.showSnackbar(message = it.message)
            }

            is QuestionUiEvent.QuestionLoadFailedEvent -> {
                snackbarHostState.showSnackbar("질문 리스트를 불러오는데 실패하였습니다")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            WSTopBar(
                title = "질문 추가/수정",
                canNavigateBack = true,
                navigateUp = { component.navigateUp() },
                action = {
                    IconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = { viewModel.toggleSearchState() },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.search),
                            contentDescription = "search_icon",
                            tint = WeSpotThemeManager.colors.txtTitleColor,
                        )
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .clickableSingle { keyboardController?.hide() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
        ) {
            AnimatedVisibility(
                visible = state.isSearchState,
                enter = expandVertically(animationSpec = tween(durationMillis = 300)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 300)) + fadeOut(),
                modifier = Modifier.zIndex(99f),
            ) {
                Box(modifier = Modifier.padding(bottom = 20.dp)) {
                    WSTextField(
                        value = state.searchInput,
                        onValueChange = viewModel::setSearchInput,
                        placeholder = "질문을 검색하세요",
                        textFieldType = WsTextFieldType.Search,
                    )
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .weight(1f),
            ) {
                items(
                    items = state.questionList,
                    key = { it.id }
                ) { item ->
                   WSListItem(
                       title = item.content,
                       subTitle = item.toTimeDescription(),
                       selected = state.clickedQuestion.id == item.id,
                       onClick = {
                           // 선택된 아이템을 다시 누르는 경우, 선택 해제
                           if (state.clickedQuestion.id == item.id) {
                               viewModel.clearQuestionClickedState()
                           } else {
                               viewModel.setQuestionClickedState(item)
                           }
                       }
                   )
               }
            }

            Box(modifier = Modifier.padding(bottom = 20.dp)) {
                WSTextField(
                    value = state.questionInput,
                    onValueChange = viewModel::setVoteQuestionInput,
                    placeholder = "추가할 질문을 입력하세요.",
                    textFieldType = WsTextFieldType.Normal,
                )
            }

            // 선택된 아이템이 존재하는 경우, 수정 상태로 간주하여 수정으로 처리한다.
            val isEditState = state.questionList.any { it.id == state.clickedQuestion.id }
            WSButton(
                onClick = {
                    if (isEditState) {
                        viewModel.editVoteQuestion()
                    } else {
                        viewModel.postVoteQuestion()
                    }
                },
                text = if (isEditState) "질문 수정하기" else "질문 추가하기",
                paddingValues = PaddingValues(),
                content = { it() },
            )
        }
    }

    LaunchedEffect(Unit) {
        component.toastMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    if (state.isLoading) {
        WSLoadingAnimation()
    }
}
