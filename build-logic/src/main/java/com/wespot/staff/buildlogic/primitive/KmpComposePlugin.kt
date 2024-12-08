package com.wespot.staff.buildlogic.primitive

import com.wespot.staff.buildlogic.configure.android
import com.wespot.staff.buildlogic.configure.compose
import com.wespot.staff.buildlogic.configure.composeCompiler
import com.wespot.staff.buildlogic.configure.kotlinMultiPlatform
import com.wespot.staff.buildlogic.configure.libs
import com.wespot.staff.buildlogic.configure.resources
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

class
KmpComposePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            compose.resources {
                publicResClass = true
            }
            composeCompiler {
                featureFlags = setOf(ComposeFeatureFlag.StrongSkipping)
            }
            if (plugins.hasPlugin("com.android.library")) {
                android {
                    buildFeatures.compose = true
                }
            }
            kotlinMultiPlatform {
                with(sourceSets) {
                    commonMain {
                        dependencies {
                            implementation(compose.dependencies.material)
                            implementation(compose.dependencies.ui)
                            implementation(compose.dependencies.components.resources)
                            implementation(compose.dependencies.components.uiToolingPreview)
                            implementation(compose.dependencies.runtime)
                            implementation(compose.dependencies.foundation)
                            implementation(compose.dependencies.material3)
                            implementation(compose.dependencies.materialIconsExtended)
                        }
                    }
                    androidMain {
                        dependencies {
                            implementation(libs.findLibrary("androidx-activity-compose").get().get())
                        }
                    }
                }
            }
        }
    }
}