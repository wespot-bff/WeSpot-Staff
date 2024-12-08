package com.wespot.staff.buildlogic.configure

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.compose: ComposeExtension
    get() = extensions["compose"] as ComposeExtension

fun Project.androidApplication(action: BaseAppModuleExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.android(action: TestedExtension.() -> Unit) {
    extensions.configure(action)
}

fun Project.kotlinAndroid(configure: KotlinAndroidProjectExtension.() -> Unit) {
    extensions.configure(configure)
}

/**  Kmp-plugin이 선언되어있는 곳에서 사용 / Kmp-plugin 미리 선언되어야 함. */
fun Project.kotlinMultiPlatform(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(action)
}

/**  Android-library plugin과 함께 사용 */
fun Project.libraryAndroidOptions(configure: LibraryAndroidComponentsExtension.() -> Unit) {
    extensions.configure(configure)
}

fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
}

fun ComposeExtension.resources(block: ResourcesExtension.() -> Unit) {
    extensions.configure<ResourcesExtension>(block)
}
