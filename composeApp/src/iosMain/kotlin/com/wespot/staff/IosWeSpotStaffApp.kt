package com.wespot.staff

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.wespot.staff.di.initKoin
import com.wespot.staff.navigation.DefaultRootComponent
import platform.UIKit.UIViewController

fun WeSpotStaffAppController(): UIViewController = ComposeUIViewController {
    initKoin()

    val root = remember { DefaultRootComponent(DefaultComponentContext(LifecycleRegistry())) }
    WeSpotStaffApp(root)
}
