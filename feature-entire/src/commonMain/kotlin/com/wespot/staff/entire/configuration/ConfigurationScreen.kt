package com.wespot.staff.entire.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wespot.staff.designsystem.component.WSListItem
import com.wespot.staff.designsystem.component.WSTopBar
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(
    component: ConfigurationComponent,
    viewModel: ConfigurationViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
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
                    onClick = { },
                )
            }
        }
    }
}
