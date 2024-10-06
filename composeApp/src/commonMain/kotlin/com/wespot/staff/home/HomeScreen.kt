package com.wespot.staff.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import bff.wespot.staff.designsystem.component.WSButton
import bff.wespot.staff.designsystem.component.WSListItem
import bff.wespot.staff.designsystem.component.WSTextField
import bff.wespot.staff.designsystem.component.WsTextFieldType
import bff.wespot.staff.designsystem.theme.StaticTypography
import bff.wespot.staff.designsystem.theme.WeSpotThemeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        backgroundColor = WeSpotThemeManager.colors.backgroundColor,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "WeSpot Staff",
                style = StaticTypography().header1,
                color = WeSpotThemeManager.colors.txtTitleColor,
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f),
            ) {
               items(state.questionList) { item ->
                   WSListItem(
                       title = item.content,
                       subTitle = item.toTimeDescription(),
                       selected = state.clickedQuestion.id == item.id,
                       onClick = { viewModel.setQuestionClickedState(item) }
                   )
               }
            }

            WSTextField(
                value = state.questionInput,
                onValueChange = viewModel::setVoteQuestionInput,
                placeholder = "추가할 질문을 입력하세요.",
                textFieldType = WsTextFieldType.Normal,
            )

            WSButton(
                onClick = { viewModel.postVoteQuestion() },
                text = "질문 추가하기",
                paddingValues = PaddingValues(),
                content = { it() },
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getVoteQuestionList()
    }

    LaunchedEffect(viewModel.uiEvent) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.uiEvent.collect {
                    when (it) {
                        is HomeUiEvent.QuestionPostEvent -> {
                            snackbarHostState.showSnackbar(message = it.message)
                        }

                        is HomeUiEvent.QuestionLoadFailedEvent -> {
                        }
                    }
                }
            }
        }
    }
}
