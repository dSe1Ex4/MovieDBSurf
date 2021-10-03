// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")

        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}")
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}