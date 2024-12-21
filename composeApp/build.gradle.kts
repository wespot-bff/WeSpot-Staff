plugins {
    alias(libs.plugins.wespot.kmp)
    alias(libs.plugins.wespot.android.application)
    alias(libs.plugins.wespot.kmp.compose)
    alias(libs.plugins.wespot.kotlin.serialization)
    alias(libs.plugins.wespot.kmp.firebase)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.android)
            implementation(libs.firebase.common)
        }
        commonMain.dependencies {
            implementation(projects.common)
            implementation(projects.designsystem)
            implementation(projects.domain)
            implementation(projects.data)
            implementation(projects.featureEntire)
            implementation(projects.featureVote)
            implementation(projects.featureReport)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kermit)

            implementation(libs.decompose)
            implementation(libs.decompose.extensions)
            implementation(libs.kotlinx.collections.immutable)
        }
    }
}

android {
    namespace = "com.wespot.staff"

    defaultConfig {
        applicationId = "com.wespot.staff"
        versionCode = 6
        versionName = "1.2.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
