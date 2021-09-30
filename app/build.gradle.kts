plugins {
    id("com.android.application")

    kotlin("android")
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
        }

    }

/*
   flavorDimensions.addAll(listOf("version"))
    productFlavors {
        create("demo") {
            dimension = "version"

            applicationIdSuffix = ".demo"
            versionNameSuffix = "-demo"
        }
        create("main"){
            dimension = "version"
        }
    }*/

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

    //UI
    implementation( "com.google.android.material:material:${Versions.material}")
    implementation( "androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation( "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}")
    implementation( "androidx.recyclerview:recyclerview:${Versions.recyclerview}")

    //Serialization
    implementation("com.google.code.gson:gson:${Versions.gson}")

    //REST
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonConvertor}")

    //TEST
    testImplementation( "junit:junit:${Versions.Tests.junit}")
    androidTestImplementation( "androidx.test.ext:junit:${Versions.Tests.extJunit}")
    androidTestImplementation( "androidx.test.espresso:espresso-core:${Versions.Tests.espresso}")
}