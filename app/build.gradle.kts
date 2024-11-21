plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.news2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.news2"
        minSdk = 28
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    val nav_version = "2.7.7"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //fragment
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    //tabLayout
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.0")
    //circle image
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //tag of circle image
    //<de.hdodenhof.circleimageview.CircleImageView
    // glide
    implementation ("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.0")
    // ViewModel
    val lifecycle_version = "2.4.1"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.activity:activity-ktx:1.4.0")
    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //progressbar
    implementation ("com.jpardogo.googleprogressbar:library:1.2.0")
}