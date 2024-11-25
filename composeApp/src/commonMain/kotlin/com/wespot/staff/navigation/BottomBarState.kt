package com.wespot.staff.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import wespotstaff.composeapp.generated.resources.Res
import wespotstaff.composeapp.generated.resources.entire
import wespotstaff.composeapp.generated.resources.entire_empty
import wespotstaff.composeapp.generated.resources.entire_tab
import wespotstaff.composeapp.generated.resources.message
import wespotstaff.composeapp.generated.resources.message_empty
import wespotstaff.composeapp.generated.resources.message_tab
import wespotstaff.composeapp.generated.resources.vote
import wespotstaff.composeapp.generated.resources.vote_empty
import wespotstaff.composeapp.generated.resources.vote_tab

internal enum class BottomBarState(
    val config: RootConfiguration,
    val icon: DrawableResource,
    val emptyIcon: DrawableResource,
    val title: StringResource,
) {
    Vote(
        config = RootConfiguration.Vote,
        icon = Res.drawable.vote_tab,
        emptyIcon = Res.drawable.vote_empty,
        title = Res.string.vote,
    ),
    Message(
        config = RootConfiguration.Message,
        icon = Res.drawable.message_tab,
        emptyIcon = Res.drawable.message_empty,
        title = Res.string.message,
    ),
    Entire(
        config = RootConfiguration.Entire,
        icon = Res.drawable.entire_tab,
        emptyIcon = Res.drawable.entire_empty,
        title = Res.string.entire,
    ),
}
