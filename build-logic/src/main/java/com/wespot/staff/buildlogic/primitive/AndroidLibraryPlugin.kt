package com.wespot.staff.buildlogic.primitive

import com.wespot.staff.buildlogic.configure.configureAndroid
import com.wespot.staff.buildlogic.configure.configureKmp
import com.wespot.staff.buildlogic.configure.libraryAndroidOptions
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            libraryAndroidOptions {
                configureAndroid()
                configureKmp()
            }
        }
    }
}
