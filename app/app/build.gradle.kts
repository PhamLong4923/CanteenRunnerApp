plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.fpt.canteenrunner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fpt.canteenrunner"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Glide dependencies
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Thêm phụ thuộc BCrypt
    implementation("org.mindrot:jbcrypt:0.4")
    //jwt
    implementation("com.auth0:java-jwt:3.18.2")
}
