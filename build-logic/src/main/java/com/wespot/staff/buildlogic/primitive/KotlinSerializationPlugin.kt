package com.wespot.staff.buildlogic.primitive

import com.wespot.staff.buildlogic.configure.kotlinMultiPlatform
import com.wespot.staff.buildlogic.configure.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            kotlinMultiPlatform {
                with(sourceSets) {
                    commonMain {
                        dependencies {
                            implementation(libs.findLibrary("kotlin-serialization-json").get().get())
                        }
                    }
                }
            }
        }
    }
}
