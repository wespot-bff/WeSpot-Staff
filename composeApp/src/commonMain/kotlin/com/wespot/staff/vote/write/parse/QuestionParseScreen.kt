package com.wespot.staff.vote.write.parse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wespot.staff.designsystem.component.WSButton
import com.wespot.staff.designsystem.component.WSTextField
import com.wespot.staff.designsystem.component.WsTextFieldType
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.domain.vote.VoteQuestionContent

@Composable
fun QuestionParseScreen(
    navigateToConfirmScreen: (String) -> Unit,
) {
    var questionListString by remember { mutableStateOf(VoteQuestionContent()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 32.dp),
            text = "질문 목록으로 변환하기",
            style = StaticTypography().header1,
            color = WeSpotThemeManager.colors.txtTitleColor,
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .weight(1f),
        ) {
            WSTextField(
                value = questionListString,
                onValueChange = { value -> questionListString = value },
                placeholder = "개행을 기준으로 목록으로 변환됩니다.",
                textFieldType = WsTextFieldType.Message,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        WSButton(
            text = "작성 완료",
            enabled = questionListString.isNotBlank(),
            onClick = { navigateToConfirmScreen(questionListString) },
            content = { it() },
        )
    }
}
