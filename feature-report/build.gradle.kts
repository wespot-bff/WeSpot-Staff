plugins {
    alias(libs.plugins.wespot.kmp.feature)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "feature-report"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common)
            implementation(projects.designsystem)
            implementation(projects.domain)

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
    namespace = "com.wespot.staff.report"
}
