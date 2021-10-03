plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")

    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "test.surf.moviedb"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val apiKeysFielder = ApiKeysFielder(projectDir.absolutePath)
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            for (field in BuildFields.RELEASE.ACTUAL_FIELDS){
                buildConfigField(field.type, field.name, "\"${field.value}\"")
            }
            for (field in apiKeysFielder.releaseBuildFields){
                buildConfigField(field.type, field.name, "\"${field.value}\"")
            }
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            for (field in BuildFields.DEBUG.ACTUAL_FIELDS){
                buildConfigField(field.type, field.name, "\"${field.value}\"")
            }
            for (field in apiKeysFielder.debugBuildFields){
                buildConfigField(field.type, field.name, "\"${field.value}\"")
            }
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    //KTX
    implementation( "androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("androidx.activity:activity-ktx:${Versions.activityKtx}")

    //UI
    implementation( "com.google.android.material:material:${Versions.material}")
    implementation( "androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation( "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}")
    implementation( "androidx.recyclerview:recyclerview:${Versions.recyclerview}")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}")
    implementation("com.github.bumptech.glide:glide:${Versions.glide}")
    kapt("com.github.bumptech.glide:compiler:${Versions.glide}")

    //Serialization
    implementation("com.google.code.gson:gson:${Versions.gson}")

    //REST
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonConvertor}")

    //DI Hilt
    implementation("com.google.dagger:hilt-android:${Versions.hilt}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt}")

    //Viewmodel & Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    kapt("androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:${Versions.datastore_preferences}")

    //TEST
    testImplementation( "junit:junit:${Versions.Tests.junit}")
    androidTestImplementation( "androidx.test.ext:junit:${Versions.Tests.extJunit}")
    androidTestImplementation( "androidx.test.espresso:espresso-core:${Versions.Tests.espresso}")
}