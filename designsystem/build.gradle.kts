plugins {
    alias(libs.plugins.wespot.kmp)
    alias(libs.plugins.wespot.android.library)
    alias(libs.plugins.wespot.kmp.compose)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "designsystem"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common)

            implementation(libs.kotlinx.collections.immutable)
        }
    }
}

android {
    namespace = "com.wespot.staff.designsystem"
}
