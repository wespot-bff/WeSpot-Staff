package com.wespot.staff.buildlogic.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11

class KmpPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }

            tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
                compilerOptions.jvmTarget.set(JVM_11)
            }
        }
    }
}
