package com.wespot.staff.vote.write.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wespot.staff.common.extensions.clickableSingle
import com.wespot.staff.common.extensions.collectSideEffect
import com.wespot.staff.designsystem.component.WSButton
import com.wespot.staff.designsystem.component.WSButtonType
import com.wespot.staff.designsystem.component.WSLoadingAnimation
import com.wespot.staff.designsystem.component.WSTextField
import com.wespot.staff.designsystem.component.WsTextFieldType
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.domain.vote.VoteQuestionContent
import org.jetbrains.compose.resources.painterResource
import wespotstaff.feature_vote.generated.resources.Res
import wespotstaff.feature_vote.generated.resources.ic_delete

@Composable
fun QuestionAddScreen(
    viewModel: QuestionAddViewModel,
    questionList: List<VoteQuestionContent>? = null,
    titleContent: @Composable () -> Unit,
    showSnackbar: (String) -> Unit,
    navigateToQuestionScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectSideEffect {
        when (it) {
            QuestionAddSideEffect.NavigateToQuestionScreen -> {
                navigateToQuestionScreen()
            }

            is QuestionAddSideEffect.ShowSnackbar -> {
                showSnackbar(it.message)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                titleContent()
            }

            itemsIndexed(items = state.questionList) { index, item ->
                if (index != 0) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        thickness = 1.dp,
                        color = WeSpotThemeManager.colors.cardBackgroundColor,
                    )
                }

                QuestionListItem(
                    index = index,
                    text = item,
                    onTextChanged = { value ->
                        viewModel.setQuestionItem(index, value)
                    },
                    onDeleteButtonClicked = viewModel::deleteQuestionItem,
                )
            }

            item {
                WSButton(
                    text = "질문 추가",
                    onClick = { viewModel.addQuestionItem() },
                    content = { it() },
                    paddingValues = PaddingValues(),
                    buttonType = WSButtonType.Tertiary,
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        WSButton(
            text = "작성 완료",
            enabled = state.questionList.isNotEmpty(),
            onClick = viewModel::submitQuestionList,
            content = { it() },
        )
    }

    if (state.isLoading) {
        WSLoadingAnimation()
    }

    LaunchedEffect(Unit) {
        questionList?.let {
            viewModel.setQuestionList(it)
        }
    }
}

@Composable
fun QuestionListItem(
    index: Int,
    text: String,
    onTextChanged: (String) -> Unit,
    onDeleteButtonClicked: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "질문 ${index + 1}",
                style = StaticTypography().body3,
                color = WeSpotThemeManager.colors.txtTitleColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier.clickableSingle {
                  onDeleteButtonClicked(index)
                },
                painter = painterResource(Res.drawable.ic_delete),
                contentDescription = "delete_button",
            )
        }

        WSTextField(
            value = text,
            onValueChange = onTextChanged,
            placeholder = "추가할 질문을 입력하세요.",
            textFieldType = WsTextFieldType.Normal,
        )
    }
}
