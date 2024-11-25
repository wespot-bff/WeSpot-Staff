import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.build.config)
}

val properties = Properties()
properties.load(rootProject.file("local.properties").inputStream())

buildConfig {
    buildConfigField<String>("BASE_URL", properties.getProperty("BASE_URL"))
    buildConfigField<String>("API_AUTHORIZATION_VALUE", properties.getProperty("API_AUTHORIZATION_VALUE"))
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
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
            implementation(libs.kotlin.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.kermit)
            implementation(libs.kotlinx.datetime)

            // Network
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.kotlin.serialization)

            // Local
            implementation(libs.datastore.prefereces)
            implementation(libs.bundles.kotlin.firebase)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            implementation(libs.datastore.prefereces)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.datastore.prefereces)
        }
    }
}

android {
    namespace = "com.wespot.staff.data"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }

    dependencies {
        coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.1.2")
    }
}
