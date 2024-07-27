plugins {
 //  alias(libs.plugins.android.application)
   id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.lgs_module.login"
    compileSdk = 34

    defaultConfig {
      //  applicationId = "com.example.lgs_module.login"
        minSdk = 24
        targetSdk = 34
     //   versionCode = 1
     //   versionName = "1.0"

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
        viewBinding =true
    }
}
kapt { arguments { arg("AROUTER_MODULE_NAME", project.name) } }

dependencies {
    implementation("com.alibaba:arouter-api:1.5.2")
    kapt("com.alibaba:arouter-compiler:1.5.2")
        // RxJava
        implementation ("io.reactivex.rxjava3:rxjava:3.1.6")
        implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")

        // Retrofit
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

        // OkHttp (Retrofit depends on OkHttp)
        implementation ("com.squareup.okhttp3:okhttp:4.9.3")
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation ("androidx.core:core:1.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}