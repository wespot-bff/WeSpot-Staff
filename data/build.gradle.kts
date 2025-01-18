import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.wespot.kmp)
    alias(libs.plugins.wespot.android.library)
    alias(libs.plugins.wespot.kotlin.serialization)
    alias(libs.plugins.build.config)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

buildConfig {
    buildConfigField<String>("BASE_URL", properties.getProperty("BASE_URL"))
    buildConfigField<String>("API_AUTHORIZATION_VALUE", properties.getProperty("API_AUTHORIZATION_VALUE"))
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "data"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)

            implementation(libs.koin.core)
            implementation(libs.kermit)
            implementation(libs.kotlinx.datetime)

            api(libs.datastore.prefereces)
            implementation(libs.bundles.ktor.shared)
            implementation(libs.bundles.kmp.firebase)
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.wespot.staff.data"
}
