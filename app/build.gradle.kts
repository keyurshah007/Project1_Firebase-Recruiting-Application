plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.project_part1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project_part1"
        minSdk = 33
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies{
    kapt ("com.github.bumptech.glide:compiler:4.11.0")
}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.firebaseui:firebase-ui-database:8.0.0")
    implementation ("com.firebaseui:firebase-ui-storage:8.0.0")
    implementation ("com.firebaseui:firebase-ui-database:8.0.0'")



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

