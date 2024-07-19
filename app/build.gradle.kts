plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // kapt/ksp choose one
    // id 'kotlin-kapt'
    id("com.google.devtools.ksp") version "2.0.0-1.0.23"
}

android {
    namespace = "com.gg.baseandstatecontrol"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gg.baseandstatecontrol"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
}

ksp {
    arg("rxhttp_package", "rxhttp")  //指定RxHttp类包名，可随意指定
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":utils"))
    implementation(project(":base"))

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.tencent.mmkv)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.okhttp)
    implementation(libs.rxhttp)
    // ksp/kapt/annotationProcessor choose one
    ksp(libs.rxhttp.compiler)
    implementation(libs.converter.moshi)
}