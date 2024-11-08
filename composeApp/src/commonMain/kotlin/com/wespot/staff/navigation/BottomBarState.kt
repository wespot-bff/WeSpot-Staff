package com.wespot.staff.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import wespotstaff.composeapp.generated.resources.Res
import wespotstaff.composeapp.generated.resources.entire_empty
import wespotstaff.composeapp.generated.resources.entire_tab
import wespotstaff.composeapp.generated.resources.home
import wespotstaff.composeapp.generated.resources.message_empty
import wespotstaff.composeapp.generated.resources.message_tab
import wespotstaff.composeapp.generated.resources.report
import wespotstaff.composeapp.generated.resources.vote
import wespotstaff.composeapp.generated.resources.vote_empty
import wespotstaff.composeapp.generated.resources.vote_tab

internal enum class BottomBarState(
    val config: RootConfiguration,
    val icon: DrawableResource,
    val emptyIcon: DrawableResource,
    val title: StringResource,
) {
    Home(
        config = RootConfiguration.Home,
        icon = Res.drawable.message_tab,
        emptyIcon = Res.drawable.message_empty,
        title = Res.string.home,
    ),
    Vote(
        config = RootConfiguration.Vote,
        icon = Res.drawable.vote_tab,
        emptyIcon = Res.drawable.vote_empty,
        title = Res.string.vote,
    ),
    Report(
        config = RootConfiguration.Report,
        icon = Res.drawable.entire_tab,
        emptyIcon = Res.drawable.entire_empty,
        title = Res.string.report,
    ),
}
