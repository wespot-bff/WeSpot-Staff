package com.wespot.staff.buildlogic.configure

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11

internal fun Project.configureAndroid() {
    android {
        namespace?.let {
            this.namespace = it
        }
        compileSdkVersion(libs.findVersion("compileSdk").get().requiredVersion.toInt())

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
            targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
            isCoreLibraryDesugaringEnabled = true
        }

        dependencies {
            "coreLibraryDesugaring"(libs.findLibrary("android-desugar-jdk").get().get())
        }

        sourceSets {
            getByName("main") {
                assets.srcDirs("src/androidMain/assets")
                java.srcDirs("src/androidMain/kotlin")
                res.srcDirs("src/androidMain/res")
            }
            getByName("test") {
                assets.srcDirs("src/androidUnitTest/assets")
                java.srcDirs("src/androidUnitTest/kotlin")
                res.srcDirs("src/androidUnitTest/res")
            }
        }
    }
}
