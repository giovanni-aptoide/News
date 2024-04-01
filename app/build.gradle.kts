plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}


android {
    namespace = "com.giovannidiluca.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.giovannidiluca.news"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    flavorDimensions += "source"
    productFlavors {
        create("bbc-news") {
            dimension = "source"
            buildConfigField("String", "SOURCE_ID", "\"bbc-news\"")

        }
        create("the-washington-post") {
            dimension = "source"
            buildConfigField("String", "SOURCE_ID", "\"the-washington-post\"")
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    //Paging
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)
    //Compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.compose.navigation)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.navigation.runtime.ktx)
    implementation(libs.kotlinx.serialization.json.jvm)
    implementation(libs.biometric.ktx)

    //Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.test.junit4)
    debugImplementation(libs.compose.test.ui.tooling)
    debugImplementation(libs.compose.test.ui.manifest)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //modules
    implementation(project(":core:data"))
    implementation(project(":core:utils"))
    implementation(project(":core:network"))
}