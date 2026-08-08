plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.bemba.tts"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.bemba.tts"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.onnxruntime.android)
}
