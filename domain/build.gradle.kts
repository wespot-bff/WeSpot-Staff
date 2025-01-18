plugins {
    alias(libs.plugins.wespot.kmp)
    alias(libs.plugins.wespot.android.library)
    alias(libs.plugins.wespot.kotlin.serialization)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "domain"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.coroutines)
        }
    }
}

android {
    namespace = "com.wespot.staff.domain"
}
