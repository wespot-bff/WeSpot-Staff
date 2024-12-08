package com.wespot.staff.buildlogic.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpFeaturePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.wespot.staff.kmp")
                apply("com.wespot.staff.androidLibrary")
                apply("com.wespot.staff.kmpCompose")
                apply("com.wespot.staff.kotlinSerialization")
            }
        }
    }
}
