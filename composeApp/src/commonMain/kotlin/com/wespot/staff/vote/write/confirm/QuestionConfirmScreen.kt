package com.wespot.staff.vote.write.confirm

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.wespot.staff.designsystem.component.WSTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionConfirmScreen(
    component: QuestionConfirmComponent,
) {
    Scaffold(
        topBar = {
            WSTopBar(
                title = "",
                canNavigateBack = true,
                navigateUp = { },
            )
        }
    ) { innerPadding ->

    }
}
