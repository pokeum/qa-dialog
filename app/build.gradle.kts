plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

@Suppress("UnstableApiUsage")
android {
    namespace = "co.pokeum.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "co.pokeum.app"
        minSdk = 24
        targetSdk = 34
        versionCode(1)
        versionName(Versions.VERSION_NAME)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(project(":inputs-dialog"))

    implementation("io.airbridge:sdk-android:4.+")

    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.ANDROID_MATERIAL)
    implementation(Libs.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Libs.ANDROIDX_APPCOMPAT)

    testImplementation(Libs.JUNIT)
}