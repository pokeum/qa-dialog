plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    namespace = "co.pokeum.inputs.dialog"

    buildToolsVersion(Versions.BUILD_TOOLS)
    compileSdkVersion(Versions.COMPILE_SDK)

    defaultConfig {
        versionCode(Versions.VERSION_CODE)
        versionName(Versions.VERSION_NAME)

        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.TARGET_SDK)

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
    @Suppress("UnstableApiUsage")
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Libs.KOTLIN_STDLIB)
    implementation(Libs.ANDROID_MATERIAL)
    implementation(Libs.ANDROIDX_APPCOMPAT)
    testImplementation(Libs.JUNIT)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "co.pokeum.inputs.dialog"
            artifactId = "qa-dialog"
            version = Versions.VERSION_NAME

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}