package com.wespot.staff.buildlogic.primitive

import com.wespot.staff.buildlogic.configure.androidApplication
import com.wespot.staff.buildlogic.configure.configureAndroid
import com.wespot.staff.buildlogic.configure.configureKmp
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            androidApplication {
                configureAndroid()
                configureKmp()
            }
        }
    }
}