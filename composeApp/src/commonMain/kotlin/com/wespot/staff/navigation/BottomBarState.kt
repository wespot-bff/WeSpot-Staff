package com.wespot.staff.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import wespotstaff.composeapp.generated.resources.Res
import wespotstaff.composeapp.generated.resources.entire
import wespotstaff.composeapp.generated.resources.entire_empty
import wespotstaff.composeapp.generated.resources.entire_tab
import wespotstaff.composeapp.generated.resources.user
import wespotstaff.composeapp.generated.resources.user_empty
import wespotstaff.composeapp.generated.resources.user_tab
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
    User(
        config = RootConfiguration.User,
        icon = Res.drawable.user_tab,
        emptyIcon = Res.drawable.user_empty,
        title = Res.string.user,
    ),
    Entire(
        config = RootConfiguration.Entire,
        icon = Res.drawable.entire_tab,
        emptyIcon = Res.drawable.entire_empty,
        title = Res.string.entire,
    ),
}
