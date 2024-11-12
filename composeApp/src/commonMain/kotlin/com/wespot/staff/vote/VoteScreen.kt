package com.wespot.staff.vote

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wespot.staff.component.NavigationListItem
import com.wespot.staff.designsystem.component.WSTopBar
import com.wespot.staff.designsystem.theme.StaticTypography
import com.wespot.staff.designsystem.theme.WeSpotThemeManager
import com.wespot.staff.vote.navigation.VoteComponent
import org.jetbrains.compose.resources.painterResource
import wespotstaff.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteScreen(
    component: VoteComponent,
) {
    Scaffold(
        topBar = {
            WSTopBar(
                title = "",
                navigation = {
                    Image(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp)
                            .size(width = 112.dp, height = 44.dp),
                        painter = painterResource(Res.drawable.main_logo),
                        contentDescription = "main_logo",
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp),
                text = "투표 관리",
                style = StaticTypography().header1,
                color = WeSpotThemeManager.colors.txtTitleColor,
            )

            NavigationListItem(
                text = "투표 질문 추가/수정",
                onClick = { }
            )

            NavigationListItem(
                text = "여러 질문 추가",
                onClick = { }
            )
        }
    }
}
