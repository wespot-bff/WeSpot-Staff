import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.wespot.staff.buildlogic"

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "17"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.compose.gradle)
    compileOnly(libs.compose.compiler.gradle)
}

gradlePlugin {
    plugins {
        register("AndroidApplication") {
            id = "com.wespot.staff.application"
            implementationClass = "com.wespot.staff.buildlogic.primitive.AndroidApplicationPlugin"
        }
        register("AndroidLibrary") {
            id = "com.wespot.staff.androidLibrary"
            implementationClass = "com.wespot.staff.buildlogic.primitive.AndroidLibraryPlugin"
        }
        register("KmpCompose") {
            id = "com.wespot.staff.kmpCompose"
            implementationClass = "com.wespot.staff.buildlogic.primitive.KmpComposePlugin"
        }
        register("KmpFirebase") {
            id = "com.wespot.staff.kmpFirebase"
            implementationClass = "com.wespot.staff.buildlogic.primitive.KmpFirebasePlugin"
        }
        register("Kmp") {
            id = "com.wespot.staff.kmp"
            implementationClass = "com.wespot.staff.buildlogic.primitive.KmpPlugin"
        }
        register("KotlinSerialization") {
            id = "com.wespot.staff.kotlinSerialization"
            implementationClass = "com.wespot.staff.buildlogic.primitive.KotlinSerializationPlugin"
        }

        register("KmpFeature") {
            id = "com.wespot.staff.kmpFeature"
            implementationClass = "com.wespot.staff.buildlogic.convention.KmpFeaturePlugin"
        }
    }
}
