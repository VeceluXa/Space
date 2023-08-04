plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.danilovfa.space"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.danilovfa.space"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":common"))

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Hilt
    implementation(libs.hilt.android)

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    kapt(libs.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.0"))
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.0")

    // RxJava
    implementation(libs.bundles.rxjava)

    // Moxy
    val moxyVersion = "2.2.2"
    kapt("com.github.moxy-community:moxy-compiler:$moxyVersion")
    implementation("com.github.moxy-community:moxy:$moxyVersion")
    implementation("com.github.moxy-community:moxy-androidx:$moxyVersion") // AppCompat
    implementation("com.github.moxy-community:moxy-material:$moxyVersion") // Bottom Sheet
    implementation("com.github.moxy-community:moxy-ktx:$moxyVersion") // Kotlin Delegate


    // Cicerone navigation
    implementation("com.github.terrakok:cicerone:7.1")

    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.window:window:1.1.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.github.MikeOrtiz:TouchImageView:3.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}