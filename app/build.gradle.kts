import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}
val keystoreProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    keystoreProperties.load(localPropertiesFile.inputStream())
}

fun getKey(propName: String): String? {
    return if (keystoreProperties.containsKey(propName)) {
        keystoreProperties.getProperty(propName)
    } else {
        System.getenv(propName)
    }
}


android {
    namespace = "com.amirali_apps.tictactoe"
    compileSdk = 36
    bundle {
        language {
            enableSplit = false
        }
    }
    defaultConfig {
        applicationId = "com.amirali_apps.tictactoe"
        minSdk = 24
        targetSdk = 36
        versionCode = 8
        versionName = "1.3.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    signingConfigs {
        create("release") {
            val keystorePath = getKey("KEYSTORE_FILE_PATH")
            if (keystorePath.isNullOrEmpty()) {
                throw GradleException("KEYSTORE_FILE_PATH is not set in local.properties or environment variables")
            }
            storeFile = file(keystorePath)
            storePassword =
                if (localPropertiesFile.exists()) keystoreProperties.getProperty("KEYSTORE_PASSWORD") else System.getenv("KEYSTORE_PASSWORD")
            keyAlias =
                if (localPropertiesFile.exists()) keystoreProperties.getProperty("KEY_ALIAS") else System.getenv("KEY_ALIAS")
            keyPassword =
                if (localPropertiesFile.exists()) keystoreProperties.getProperty("KEY_PASSWORD") else System.getenv("KEY_PASSWORD")
        }
    }



    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
    flavorDimensions += listOf("store")

    productFlavors {
        create("bazaar") {
            dimension = "store"
        }
        create("myket") {
            dimension = "store"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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
    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.converter.gson)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
kapt {
    correctErrorTypes = true
}