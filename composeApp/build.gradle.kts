import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val voyagerVersion = "1.1.0-beta02"
        val koinVersion = "4.1.1"

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")

            // Koin Dependency Injection
            implementation("io.insert-koin:koin-compose:${koinVersion}")
            implementation("io.insert-koin:koin-compose-viewmodel:${koinVersion}")

            // Voyager Navigation
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

            // Datastore
            implementation("androidx.datastore:datastore:1.2.0")
            implementation("androidx.datastore:datastore-preferences:1.2.0")

            // Room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.assertk)
        }
    }
}

android {
    namespace = "com.andydotdaniel.jajanku"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.andydotdaniel.jajanku"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    add("kspAndroid", "androidx.room:room-compiler:2.6.1")
    add("kspIosArm64", "androidx.room:room-compiler:2.6.1")
    add("kspIosSimulatorArm64", "androidx.room:room-compiler:2.6.1")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
