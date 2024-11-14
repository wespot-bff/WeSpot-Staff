package com.wespot.staff.vote.write

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.wespot.staff.designsystem.component.WSHomeTabRow
import com.wespot.staff.designsystem.component.WSTopBar
import com.wespot.staff.vote.QuestionWriteComponent
import com.wespot.staff.vote.write.add.QuestionAddScreen
import com.wespot.staff.vote.write.add.QuestionAddViewModel
import com.wespot.staff.vote.write.parse.QuestionParseScreen
import com.wespot.staff.vote.write.parse.QuestionParseViewModel
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
    parseViewModel: QuestionParseViewModel = koinViewModel(),
) {
    val tabList = persistentListOf(
        stringResource(Res.string.tab_add),
        stringResource(Res.string.tab_parse),
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            WSTopBar(
                title = "",
                canNavigateBack = true,
                navigateUp = component::navigateUp,
            )
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
                        QuestionAddScreen(viewModel = addViewModel)
                    }

                    QUESTION_PARSE_SCREEN_INDEX -> {
                        QuestionParseScreen(viewModel = parseViewModel)
                    }
                }
            }
        }
    }
}

const val QUESTION_ADD_SCREEN_INDEX = 0
const val QUESTION_PARSE_SCREEN_INDEX = 1
