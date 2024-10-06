package com.wespot.staff

import androidx.compose.ui.window.ComposeUIViewController
import com.wespot.staff.di.initKoin
import platform.UIKit.UIViewController

fun WeSpotStaffAppController(): UIViewController = ComposeUIViewController {
    initKoin()
    WeSpotStaffApp()
}
